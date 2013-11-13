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

import java.util.concurrent.Future;

import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.HelloService;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.SayHelloInput;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.SayHelloOutput;
import org.opendaylight.yangtools.yang.common.RpcResult;

/**
 * @author David Bainbridge <davidk.bainbridge@gmail.com>
 * 
 */
public class HelloServiceImpl implements HelloService {
    private NotificationProviderService notificationProvider = null;

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
    public Future<RpcResult<SayHelloOutput>> sayHello(SayHelloInput arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public void setNotificationProvider(
            NotificationProviderService notificationProvider) {
        this.notificationProvider = notificationProvider;
    }

    public final NotificationProviderService getNotificationProvider() {
        return notificationProvider;
    }
}
