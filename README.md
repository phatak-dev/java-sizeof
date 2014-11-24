This is a memory consumption estimator for Scala/Java. This library is ported from [Spark](https://github.com/apache/spark) project.

## Dependency

###Sbt

    libraryDependencies += "com.madhukaraphatak" %% "java-sizeof" % "0.1"

supported for both 2.10 and 2.11.

###Maven

     <dependency>
     <groupId>com.madhukaraphatak</groupId>
     <artifactId>java-sizeof_2.11</artifactId>
     <version>0.1</version>
     </dependency>


## Usage

For any object, estimate object size by calling

    SizeEstimator.estimate(obj);


For more examples look at *examples* folder.