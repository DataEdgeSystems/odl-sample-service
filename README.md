OpenDayLight SDN Controller Sample Service
==================

This project represents a sample or example of an
[OpenDayLight (ODL)](http://www.opendaylight.org/) service
that exposes its capabilities not only as an Model Drive Service Activation
Layer (MD-SAL) capability, but also through a [REST](http://en.wikipedia.org/wiki/Representational_state_transfer)
and CLI interface.

The sample service is made up of four parts:

- __Model__ - the model defines the data types and service RPCs in an implementation
    neutral notation using the [yang modeling language](https://tools.ietf.org/html/rfc6020)
- __Provider__ - this is an implementation of the service based on the interfaces generated from the model.
- __Consumer__ - this is really a test bundle that can respond to notifications sent out from the HelloProvider.
- __NB API__ - this is a RESTful interface into the capabilities of the service,
    *note: there need not be a one-to-one correlation between the service interface and the NB API*   
- __CLI__ - this exposes the service capability to the CLI. This is current TDB and I have not looked into how this is actually done. 

Project Structure
====
The project is physically structured into 5 peer maven modules under a common
directory. The common directory contains a maven POM file that will build all
the modules, but this POM does not represent a "parent" artifact to the modules.
Instead the common POM is just for convenience.

For this project the "parent" module is a peer for each of other modules. This
is merely a preference and different developers may follow different patterns.

Each module that corresponds to a logical ODL component will be built and
assembled into an OSGi bundle such that it can be installed into the ODL OSGi
container. To generate the bundles a `mvn install` command should suffice.

Installing Bundles
====
To install the service bundles into an ODL OSGi container, you first start ODL
and then install and start each of the bundles. Please see the ODL documentation
to understand how to download, build, install, and start ODL. Once that is
complete, command similar to the following should install and start the
sample service: (note: some output has been omitted)

        osgi> install file:///home/joe/src/odl-sample-service/hello-model/target/hello-model-0.0.1-SNAPSHOT.jar
        
        Key                  229
        ProtectionDomain     null
        Location             file:///home/joe/src/odl-sample-service/hello-model/target/hello-model-0.0.1-SNAPSHOT.jar
        State                32
        Bundle               229|Active     |    1|com.company.opendaylight.controller.hello-model (0.0.1.SNAPSHOT)
        Version              0.0.1.SNAPSHOT
        StateChanging        null
        BundleContext        org.eclipse.osgi.framework.internal.core.BundleContextImpl@6e01944c
        BundleId             229
        StartLevel           1
        SymbolicName         com.company.opendaylight.controller.hello-model
        BundleData           com.company.opendaylight.controller.hello-model_0.0.1.SNAPSHOT
        KeyHashCode          229
        BundleDescription    com.company.opendaylight.controller.hello-model_0.0.1.SNAPSHOT
        Framework            org.eclipse.osgi.framework.internal.core.Framework@6d5e2115
        ResolutionFailureException org.osgi.framework.BundleException: The state indicates the bundle is resolved
        Revisions            [com.company.opendaylight.controller.hello-model_0.0.1.SNAPSHOT]
        
        osgi> start 229
        
        
        osgi> install file:///home/joe/src/odl-sample-service/hello-provider/target/hello-provider-0.0.1-SNAPSHOT.jar
        
        Key                  230
        ProtectionDomain     null
        Location             file:///home/joe/src/odl-sample-service/hello-provider/target/hello-provider-0.0.1-SNAPSHOT.jar
        State                32
        Bundle               230|Active     |    1|com.company.opendaylight.controller.hello-provider (0.0.1.SNAPSHOT)
        Version              0.0.1.SNAPSHOT
        StateChanging        null
        BundleContext        org.eclipse.osgi.framework.internal.core.BundleContextImpl@6125f739
        BundleId             230
        StartLevel           1
        SymbolicName         com.company.opendaylight.controller.hello-provider
        BundleData           com.company.opendaylight.controller.hello-provider_0.0.1.SNAPSHOT
        KeyHashCode          230
        BundleDescription    com.company.opendaylight.controller.hello-provider_0.0.1.SNAPSHOT
        Framework            org.eclipse.osgi.framework.internal.core.Framework@6d5e2115
        ResolutionFailureException org.osgi.framework.BundleException: The state indicates the bundle is resolved
        Revisions            [com.company.opendaylight.controller.hello-provider_0.0.1.SNAPSHOT]
        
        osgi> start 230
        
        
        osgi> install file:///home/joe/src/odl-sample-service/hello-consumer/target/hello-consumer-0.0.1-SNAPSHOT.jar
        
        Key                  231
        ProtectionDomain     null
        Location             file:///home/joe/src/odl-sample-service/hello-consumer/target/hello-consumer-0.0.1-SNAPSHOT.jar
        State                32
        Bundle               231|Active     |    1|com.company.opendaylight.controller.hello-consumer (0.0.1.SNAPSHOT)
        Version              0.0.1.SNAPSHOT
        StateChanging        null
        BundleContext        org.eclipse.osgi.framework.internal.core.BundleContextImpl@43abb4ec
        BundleId             232
        StartLevel           1
        SymbolicName         com.company.opendaylight.controller.hello-consumer
        BundleData           com.company.opendaylight.controller.hello-consumer_0.0.1.SNAPSHOT
        KeyHashCode          231
        BundleDescription    com.company.opendaylight.controller.hello-consumer_0.0.1.SNAPSHOT
        Framework            org.eclipse.osgi.framework.internal.core.Framework@6d5e2115
        ResolutionFailureException org.osgi.framework.BundleException: The state indicates the bundle is resolved
        Revisions            [com.company.opendaylight.controller.hello-consumer_0.0.1.SNAPSHOT]
        
        osgi> start 231
                
        
        osgi> install file:///home/joe/src/odl-sample-service/hello-nbapi/target/hello-nbapi-0.0.1-SNAPSHOT.jar
        
        Key                  232
        ProtectionDomain     null
        Location             file:///home/joe/src/odl-sample-service/hello-nbapi/target/hello-nbapi-0.0.1-SNAPSHOT.jar
        State                32
        Bundle               231|Active     |    1|com.company.opendaylight.controller.hello-nbapi (0.0.1.SNAPSHOT)
        Version              0.0.1.SNAPSHOT
        StateChanging        null
        BundleContext        org.eclipse.osgi.framework.internal.core.BundleContextImpl@43abb4ec
        BundleId             232
        StartLevel           1
        SymbolicName         com.company.opendaylight.controller.hello-nbapi
        BundleData           com.company.opendaylight.controller.hello-nbapi_0.0.1.SNAPSHOT
        KeyHashCode          232
        BundleDescription    com.company.opendaylight.controller.hello-nbapi_0.0.1.SNAPSHOT
        Framework            org.eclipse.osgi.framework.internal.core.Framework@6d5e2115
        ResolutionFailureException org.osgi.framework.BundleException: The state indicates the bundle is resolved
        Revisions            [com.company.opendaylight.controller.hello-nbapi_0.0.1.SNAPSHOT]
        
        osgi> start 232
        
Testing Service
====
To test the service you can issue a simple HTTP GET request from either a browser of the command line using curl.

        $ curl -v http://admin:admin@localhost:8080/controller/nb/v1/hello/default/hello/123
        * Adding handle: conn: 0xb9baf0
        * Adding handle: send: 0
        * Adding handle: recv: 0
        * Curl_addHandleToPipeline: length: 1
        * - Conn 0 (0xb9baf0) send_pipe: 1, recv_pipe: 0
        * About to connect() to localhost port 8080 (#0)
        *   Trying 127.0.0.1...
        * Connected to localhost (127.0.0.1) port 8080 (#0)
        * Server auth using Basic with user 'admin'
        > GET /controller/nb/v1/hello/default/hello/123 HTTP/1.1
        > Authorization: Basic YWRtaW46YWRtaW4=
        > User-Agent: curl/7.32.0
        > Host: localhost:8080
        > Accept: */*
        > 
        < HTTP/1.1 200 OK
        * Server Apache-Coyote/1.1 is not blacklisted
        < Server: Apache-Coyote/1.1
        < Cache-Control: private
        < Expires: Wed, 31 Dec 1969 16:00:00 PST
        < Set-Cookie: JSESSIONIDSSO=AF037BBEFDDDEA2C637C1941C346D3EA; Path=/
        < Set-Cookie: JSESSIONID=4087ADAB87E9590124D2A28DC3A1B957; Path=/
        < Content-Length: 0
        < Date: Thu, 14 Nov 2013 02:00:40 GMT
        < 
        * Connection #0 to host localhost left intact        
        
