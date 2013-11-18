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

import org.opendaylight.controller.sal.binding.api.AbstractBindingAwareProvider;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.RpcRegistration;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.yang.gen.v1.http.controller.opendaylight.company.com.ns.model.hello.rev131113.HelloService;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author David Bainbridge <davidk.bainbridge@gmail.com>
 * 
 */
public class HelloProvider extends AbstractBindingAwareProvider {
    private static final Logger log = LoggerFactory
            .getLogger(HelloProvider.class);

    private ProviderContext providerContext = null;
    private HelloServiceImpl service = null;
    private RpcRegistration<HelloService> registration = null;

    public HelloProvider() {
        service = new HelloServiceImpl();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.controller.sal.binding.api.BindingAwareProvider#
     * onSessionInitiated
     * (org.opendaylight.controller.sal.binding.api.BindingAwareBroker
     * .ProviderContext)
     */
    @Override
    public void onSessionInitiated(ProviderContext providerContext) {
        try {
            this.providerContext = providerContext;
            service.setNotificationProvider(this.providerContext
                    .getSALService(NotificationProviderService.class));
            registration = providerContext.addRpcImplementation(
                    HelloService.class, service);
            log.info(
                    "Registered 'HelloService' implementation '{}' with registration '{}'",
                    service, registration);
        } catch (IllegalStateException e) {
            log.error("Unable to register 'HelloService' implementation", e);
        }
    }

    @Override
    protected void stopImpl(BundleContext context) {
        if (registration != null) {
            try {
                log.info(
                        "Closing 'HelloService' implementation '{}' with registration '{}'",
                        service, registration);
                registration.close();
                registration = null;
            } catch (Exception e) {
                log.error(
                        "Failed to close 'HelloService' registration, may lead to mulitple implementation registrations and errors",
                        e);
            }
        } else {
            log.info("'HelloService' implementation being stopped, but implementation is not registered.");
        }
    }

    public final ProviderContext getProviderContext() {
        return providerContext;
    }

    public final HelloService getService() {
        return service;
    }
}