# Camel-Arina-MapLab is a component for Apache Camel Framework.

### Supported mapping formats
- MFD - Altova MapForce xml file format.

# Usage syntax
`<to uri = "arina-maplab.mapforce: <transformation scheme>? [Mode = cache | refresh] & [refreshDelay = valueInMilliseconds] & [validateModel = true | false]" />`
  - **arina-maplab.mapforce** - name of the camel component.
  - **`<transformation scheme>`** - path to the model file to be executed. If several model files are used, they must be indicated one after another, separated by a comma "," or a semicolon ";". It is also possible to specify a list of model file masks, for example file: config/mappings/m1*.mfd;classpath:config/mappings/m2*.mfd

The following formats are supported:
1. file: - read from files (mask supported)
2. classpath: - reading from resources in jar files (mask is supported)
3. http: or https: - read by resource URL via http or https
4. ftp: or ftps: - read by resource URL via ftp or ftps

- **mode** = **cache** | **refresh** - component operation mode.

  1. **cache** - load the transformation model at the first call and use it until the Camel context stops. Used by default.
  
  2. **refresh** - load the transformation model at the first call and update it after a period of time specified by the refreshDelay parameter has expired.

By default, **refreshDelay** = 0, that is, the model will be updated with each transformation.

- **refreshDelay** = valueInMilliseconds - the time period in milliseconds after which the model will be updated. Default = 0. It is convenient to use when debugging models.
- **validateModel** = true | false - check the model for consistency before loading. The default is true. The verification process takes about 2 seconds; therefore, it is not recommended to use verification in an industrial environment if refresh mode is used.

## Note
When specifying the path to the model files, you must specify the protocol prefix file :, classpath: etc.

You can read more detailed information here https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/support/PathMatchingResourcePatternResolver.html

# Example
```xml
<route autoStartup="true">
    <from uri="file:_in_out_/in?include=.*&amp;delete=true&amp;moveFailed=.failed/${file:name}"/>
    <to uri="arina-maplab.mapforce:file:config/mappings/example-model.mfd"/>
    <to uri="file:_in_out_/out?fileName=${file:name}"/>
</route>
```
