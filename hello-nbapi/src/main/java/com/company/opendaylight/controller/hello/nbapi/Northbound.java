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

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.codehaus.enunciate.jaxrs.TypeHint;
import org.opendaylight.controller.sal.utils.ServiceHelper;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.CreateHelloInputBuilder;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.CreateHelloOutput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.DeleteHelloInputBuilder;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.DeleteHelloOutput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.GetHelloInput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.GetHelloInputBuilder;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.GetHelloOutput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.HelloService;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.SayHelloInput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.SayHelloInputBuilder;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.SayHelloOutput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.UpdateHelloInputBuilder;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.UpdateHelloOutput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.update.hello.input.HelloBuilder;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.opendaylight.controller.hello.dto.HelloDTO;

/**
 * @author David Bainbridge <davidk.bainbridge@gmail.com>
 * 
 */
@Path("/")
public class Northbound {
    private static final Logger log = LoggerFactory.getLogger(Northbound.class);

    @Path("/{containerName}/request/{nodeId}")
    @GET
    public Response helloRequest(
            @PathParam("containerName") String containerName,
            @PathParam("nodeId") String nodeId) {
        HelloService service = (HelloService) ServiceHelper.getGlobalInstance(
                HelloService.class, this);

        if (log.isDebugEnabled()) {
            log.debug(
                    "INVOKE: 'getHello' with container = '{}' and nodeid = '{}'",
                    containerName, nodeId);
            try {
                Field f = service.getClass().getField("_delegate");
                log.debug(
                        "  USING: 'getHello' implementation '{}' and delegate '{}'",
                        service, f.get(service));
            } catch (Exception e) {
                log.error("  ERROR: NO DELEGATE, expect failure", e);
            }
        }
        SayHelloInput input = new SayHelloInputBuilder().setNodeId(null)
                .build();
        Future<RpcResult<SayHelloOutput>> output = service.sayHello(input);
        RpcResult<SayHelloOutput> result;
        try {
            result = output.get();
        } catch (InterruptedException | ExecutionException e) {
            return Response.serverError().build();
        }
        log.info("RESULT: '{}'", result.getResult().getNodeResponse()
                .toString());
        return Response.ok().build();
    }

    @Path("/{containerName}/hello/{helloId}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @TypeHint(HelloDTO.class)
    public Response getHello(@PathParam("containerName") String containerName,
            @PathParam("helloId") String helloId) {
        HelloService service = (HelloService) ServiceHelper.getGlobalInstance(
                HelloService.class, this);

        GetHelloInput input = new GetHelloInputBuilder().setHelloId(helloId)
                .build();
        Future<RpcResult<GetHelloOutput>> output = service.getHello(input);
        try {
            if (output.get().getResult() != null) {
                log.debug("RESULT: {}", output.get().getResult().getName());
                return Response.ok(
                        new HelloDTO(output.get().getResult().getName(),
                                output.get().getResult().getValue())).build();
            } else {
                return Response.status(404).build();
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("Unexpected exception when attempting to GET", e);
            return Response.serverError().build();
        }
    }

    @Path("/{containerName}/hello/{helloId}")
    @PUT
    public Response putHello(@Context UriInfo ui,
            @PathParam("containerName") String containerName,
            @PathParam("helloId") String helloId, HelloDTO hello) {

        if (helloId == null || helloId.trim().length() == 0) {
            return Response.status(400).build();
        }

        HelloService service = (HelloService) ServiceHelper.getGlobalInstance(
                HelloService.class, this);
        UpdateHelloInputBuilder builder = new UpdateHelloInputBuilder();
        builder.setHelloId(helloId);
        HelloBuilder hbuild = new HelloBuilder();
        hbuild.fieldsFrom(hello);
        builder.setHello(hbuild.build());
        Future<RpcResult<UpdateHelloOutput>> output = service
                .updateHello(builder.build());
        try {
            switch (output.get().getResult().getStatus()) {
            case Ok:
                return Response.ok().build();
            case Created:
                return Response
                        .created(
                                ui.getBaseUriBuilder().path(containerName)
                                        .path("hello")
                                        .path(output.get().getResult().getId())
                                        .build()).build();
            case Conflict:
                return Response.status(409).build();
            case BadRequest:
                return Response.status(400).build();
            default:
                log.error("Unexpected response from service");
                return Response.serverError().build();
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("Unable to update", e);
            return Response.serverError().build();
        }
    }

    @Path("/{containerName}/hello")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response postHello(@Context UriInfo ui,
            @PathParam("containerName") String containerName, HelloDTO hello) {
        HelloService service = (HelloService) ServiceHelper.getGlobalInstance(
                HelloService.class, this);
        CreateHelloInputBuilder builder = new CreateHelloInputBuilder();
        builder.fieldsFrom(hello);
        Future<RpcResult<CreateHelloOutput>> output = service
                .createHello(builder.build());
        try {
            String id = output.get().getResult().getHelloId();
            log.info("ID: {}", id);
            log.info("BASE URI: {}", ui.getBaseUriBuilder().path(containerName)
                    .path("hello").path(id).build());
            return Response.created(
                    ui.getBaseUriBuilder().path(containerName).path("hello")
                            .path(id).build()).build();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Unable to create resources", e);
            return Response.serverError().build();
        }
    }

    @Path("/{containerName}/hello/{helloId}")
    @DELETE
    public Response deleteHello(
            @PathParam("containerName") String containerName,
            @PathParam("helloId") String helloId) {
        HelloService service = (HelloService) ServiceHelper.getGlobalInstance(
                HelloService.class, this);
        DeleteHelloInputBuilder builder = new DeleteHelloInputBuilder();
        builder.setHelloId(helloId);
        log.debug("Attempting to delete {}", helloId);
        Future<RpcResult<DeleteHelloOutput>> output = service
                .deleteHello(builder.build());
        log.debug("Call complete {}", output);
        try {
            if (output != null && output.get().isSuccessful()
                    && output.get().getResult().isFound()) {
                return Response.ok().build();
            } else {
                return Response.status(404).build();
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("Delete failed", e);
            return Response.serverError().build();
        }
    }
}