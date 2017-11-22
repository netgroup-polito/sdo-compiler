# sdo-compiler

This Java program generate some Java classes from a service description model.

Params required:
- model_filename: The path of the JSON file that contains the service description model
- java_package: The Java package of the output classes (if you are using Polito SDO-module use it.polito.netgroup.selforchestratingservices.auto)
- base_directory: Where the ouput Java classes are saved (if you are using Polito SDO-module use SDO-module-path/src/main/java/)

All the dependencies are listed inside the pom.xml file.
