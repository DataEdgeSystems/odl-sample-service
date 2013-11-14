/*
 * Copyright (c) 2013, project authors and/or its affiliates.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of the authors or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.company.opendaylight.controller.hello.nbapi;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.opendaylight.controller.sal.utils.ServiceHelper;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.HelloService;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.SayHelloInput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.SayHelloInputBuilder;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.SayHelloOutput;
import org.opendaylight.yangtools.yang.common.RpcResult;

/**
 * @author David Bainbridge <davidk.bainbridge@gmail.com>
 * 
 */
@Path("/")
public class Northbound {
    @Path("/{containerName}/hello/{nodeId}")
    @GET
    public Response getHello(@PathParam("containerName") String containerName,
            @PathParam("nodeId") String nodeId) {
        HelloService service = (HelloService) ServiceHelper.getGlobalInstance(
                HelloService.class, this);
        System.err.println("SERVICE: " + service);
        System.err.println("CONTAINER: " + containerName);
        System.err.println("NODEID: " + nodeId);
        SayHelloInput input = new SayHelloInputBuilder().setNodeId(null)
                .build();
        for (Field m : service.getClass().getFields()) {
            System.err.println("  " + m.getName());
            if ("_delegate".equals(m.getName())) {
                try {
                    System.err.println("DELEGATE: " + m.get(service));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        Future<RpcResult<SayHelloOutput>> output = service.sayHello(input);
        RpcResult<SayHelloOutput> result;
        try {
            result = output.get();
        } catch (InterruptedException | ExecutionException e) {
            return Response.serverError().build();
        }
        System.err.println("RESULT: " + result.getResult().getNodeResponse());
        return Response.ok().build();
    }

    @Path("/{containerName}/hello/{nodeId}")
    @PUT
    public Response putHello(@PathParam("containerName") String containerName,
            @PathParam("nodeId") String nodeId) {
        return Response.ok().build();
    }

    @Path("/{containerName}/hello/{nodeId}")
    @POST
    public Response postHello(@PathParam("containerName") String containerName,
            @PathParam("nodeId") String nodeId) {
        return Response.ok().build();
    }

    @Path("/{containerName}/hello/{nodeId}")
    @DELETE
    public Response deleteHello(
            @PathParam("containerName") String containerName,
            @PathParam("nodeId") String nodeId) {
        return Response.ok().build();
    }
}