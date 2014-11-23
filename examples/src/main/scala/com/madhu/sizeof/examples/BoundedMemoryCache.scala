package com.madhu.sizeof.examples

import java.util.LinkedHashMap

import com.madhu.sizeof.SizeEstimator
import org.apache.log4j.Logger

/**
 * An implementation of Cache that estimates the sizes of its entries and
 * attempts to limit its total memory usage to a fraction of the JVM heap.
 * Objects' sizes are estimated using SizeEstimator, which has limitations;
 * most notably, we will overestimate total memory used if some cache
 * entries have pointers to a shared object. Nonetheless, this Cache should
 * work well when most of the space is used by arrays of primitives or of
 * simple classes.
 */
class BoundedMemoryCache {
  private val maxBytes: Long = getMaxBytes()
  private val logger = Logger.getLogger(classOf[BoundedMemoryCache])
  logger.info("BoundedMemoryCache.maxBytes = " + maxBytes)

  private var currentBytes = 0L
  private val map = new LinkedHashMap[Any, Entry](32, 0.75f, true)

  // An entry in our map; stores a cached object and its size in bytes
  class Entry(val value: Any, val size: Long) {}

  def get(key: Any): Any = {
    synchronized {
      val entry = map.get(key)
      if (entry != null) entry.value else null
    }
  }

  def put(key: Any, value: Any) {
    logger.info("Asked to add key " + key)
    val startTime = System.currentTimeMillis
    val size = SizeEstimator.estimate(value.asInstanceOf[AnyRef])
    val timeTaken = System.currentTimeMillis - startTime
    logger.info("Estimated size for key %s is %d".format(key, size))
    logger.info("Size estimation for key %s took %d ms".format(key, timeTaken))
    synchronized {
      ensureFreeSpace(size)
      logger.info("Adding key " + key)
      map.put(key, new Entry(value, size))
      currentBytes += size
      logger.info("Number of entries is now " + map.size)
    }
  }

  private def getMaxBytes(): Long = {
    val memoryFractionToUse = System.getProperty(
      "boundedMemoryCache.memoryFraction", "0.75").toDouble
    (Runtime.getRuntime.totalMemory * memoryFractionToUse).toLong
  }

  /**
   * Remove least recently used entries from the map until at least space
   * bytes are free. Assumes that a lock is held on the BoundedMemoryCache.
   */
  private def ensureFreeSpace(space: Long) {
    logger.info("ensureFreeSpace(%d) called with curBytes=%d, maxBytes=%d".format(
      space, currentBytes, maxBytes))
    val iter = map.entrySet.iterator
    while (maxBytes - currentBytes < space && iter.hasNext) {
      val mapEntry = iter.next()
      dropEntry(mapEntry.getKey, mapEntry.getValue)
      currentBytes -= mapEntry.getValue.size
      iter.remove()
    }
  }

  protected def dropEntry(key: Any, entry: Entry) {
    logger.info("Dropping key %s of size %d to make space".format(
      key, entry.size))
  }

}

/**
 * This code shows how to use the cache
 */
object BoundedMemoryCache {
  def main(args: Array[String]) {
    //define the percentage of vm to be used
    System.setProperty("boundedMemoryCache.memoryFraction","0.01")
    val cache = new BoundedMemoryCache

    //put some values
    cache.put("simpleKey","hello")
    cache.put("bigKey",0 to 10000 map (index => index+1))
    //cache starts dropping keys to get more space
    cache.put("moreBigger", 0 to 100000 map (index => index+1))

  }

}

