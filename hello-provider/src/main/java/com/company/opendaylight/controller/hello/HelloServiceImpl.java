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
package com.company.opendaylight.controller.hello;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.controller.sal.common.util.Futures;
import org.opendaylight.controller.sal.common.util.Rpcs;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.CreateHelloInput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.CreateHelloOutput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.CreateHelloOutputBuilder;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.DeleteHelloInput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.DeleteHelloOutput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.DeleteHelloOutputBuilder;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.GetHelloInput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.GetHelloOutput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.GetHelloOutputBuilder;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.HelloDone;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.HelloDoneBuilder;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.HelloService;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.SayHelloInput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.SayHelloOutput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.SayHelloOutputBuilder;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.UpdateHelloInput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.UpdateHelloOutput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.UpdateHelloOutput.Status;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.UpdateHelloOutputBuilder;
import org.opendaylight.yangtools.yang.common.RpcError;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.opendaylight.controller.hello.dto.HelloDTO;

/**
 * @author David Bainbridge <davidk.bainbridge@gmail.com>
 * 
 */
public class HelloServiceImpl implements HelloService {
    private static final Logger log = LoggerFactory
            .getLogger(HelloServiceImpl.class);

    private Map<String, HelloDTO> hellos = new ConcurrentHashMap<String, HelloDTO>();

    private NotificationProviderService notificationProvider = null;

    public void setNotificationProvider(
            NotificationProviderService notificationProvider) {
        this.notificationProvider = notificationProvider;
    }

    public final NotificationProviderService getNotificationProvider() {
        return notificationProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com
     * .ns.model
     * .hello.rev131113.HelloService#sayHello(org.opendaylight.yang.gen.
     * v1.http.controller
     * .opendaylight.company.com.ns.model.hello.rev131113.SayHelloInput)
     */
    @Override
    public Future<RpcResult<SayHelloOutput>> sayHello(SayHelloInput input) {

        log.debug("INVOKE: 'sayHello'");
        SayHelloOutput result = new SayHelloOutputBuilder().setNodeResponse(
                "Hello, World?").build();

        if (notificationProvider != null) {
            HelloDone hd = new HelloDoneBuilder().setStatus(
                    HelloDone.Status.Success).build();
            notificationProvider.publish(hd);
        }
        return Futures.immediateFuture(Rpcs.getRpcResult(true, result,
                new ArrayList<RpcError>()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com
     * .ns.model
     * .hello.rev131113.HelloService#createHello(org.opendaylight.yang.gen
     * .v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.
     * CreateHelloInput)
     */
    @Override
    public Future<RpcResult<CreateHelloOutput>> createHello(
            CreateHelloInput input) {
        log.debug("INPUT: {}", input);
        HelloDTO h = new HelloDTO(input.getName(), input.getValue());
        String id = UUID.randomUUID().toString();
        hellos.put(id, h);
        log.debug("PUT {} at {}", h, id);
        return Futures.immediateFuture(Rpcs.getRpcResult(true,
                new CreateHelloOutputBuilder().setHelloId(id).build(),
                new ArrayList<RpcError>()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com
     * .ns.model
     * .hello.rev131113.HelloService#deleteHello(org.opendaylight.yang.gen
     * .v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.
     * DeleteHelloInput)
     */
    @Override
    public Future<RpcResult<DeleteHelloOutput>> deleteHello(
            DeleteHelloInput input) {
        try {
            log.error("In DELETE {}", input);
            HelloDTO h = hellos.remove(input.getHelloId());
            if (h != null) {
                log.debug("DELETED: {}", input.getHelloId());
            } else {
                log.debug("NOT FOUND {}", input.getHelloId());
            }
            return Futures.immediateFuture(Rpcs.getRpcResult(true,
                    new DeleteHelloOutputBuilder().setFound(h != null).build(),
                    new ArrayList<RpcError>()));
        } catch (Exception e) {
            log.error("WHAT", e);
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com
     * .ns.model
     * .hello.rev131113.HelloService#getHello(org.opendaylight.yang.gen.
     * v1.http.controller
     * .opendaylight.company.com.ns.model.hello.rev131113.GetHelloInput)
     */
    @Override
    public Future<RpcResult<GetHelloOutput>> getHello(GetHelloInput input) {
        HelloDTO h = hellos.get(input.getHelloId());
        log.debug("GET in {} from {} value {}", this, input.getHelloId(), h);

        GetHelloOutput output = null;
        if (h != null) {
            GetHelloOutputBuilder builder = new GetHelloOutputBuilder();
            builder.fieldsFrom(h);
            output = builder.build();
        }
        return Futures.immediateFuture(Rpcs.getRpcResult(true, output,
                new ArrayList<RpcError>()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com
     * .ns.model
     * .hello.rev131113.HelloService#updateHello(org.opendaylight.yang.gen
     * .v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.
     * UpdateHelloInput)
     */
    @Override
    public Future<RpcResult<UpdateHelloOutput>> updateHello(
            UpdateHelloInput input) {
        // An Update can be a create or an update.
        String id = input.getHelloId();
        HelloDTO h = hellos.get(id);
        if (h == null) {
            // Need to create it with the specified ID.
            h = new HelloDTO(input.getHello().getName(), input.getHello()
                    .getValue());
            hellos.put(id, h);
            log.debug("PUT CREATED {} at {}", h, id);
            return Futures.immediateFuture(Rpcs.getRpcResult(true,
                    new UpdateHelloOutputBuilder().setStatus(Status.Created)
                            .setId(id).build(), new ArrayList<RpcError>()));
        } else {
            // Need to update the Hello resource
            h.setName(input.getHello().getName());
            h.setValue(input.getHello().getValue());
            log.debug("PUT UPDATED {} at {}", h, id);
            return Futures.immediateFuture(Rpcs
                    .getRpcResult(true, new UpdateHelloOutputBuilder()
                            .setStatus(Status.Ok).build(),
                            new ArrayList<RpcError>()));
        }
    }
}
