/*
 * Copyright (c) 2008-2015, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.client.impl.protocol.parameters;

import com.hazelcast.client.impl.protocol.ClientMessage;
import com.hazelcast.client.impl.protocol.ClientMessageType;

public class RegisterMembershipListenerParameters {

    public static final ClientMessageType TYPE = ClientMessageType.REGISTER_MEMBERSHIP_LISTENER_REQUEST;

    private RegisterMembershipListenerParameters(ClientMessage flyweight) {

    }

    public static RegisterMembershipListenerParameters decode(ClientMessage flyweight) {
        return new RegisterMembershipListenerParameters(flyweight);
    }

    public static ClientMessage encode() {
        final int requiredDataSize = calculateDataSize();
        ClientMessage clientMessage = ClientMessage.createForEncode(requiredDataSize);
        clientMessage.setMessageType(TYPE.id());
        clientMessage.ensureCapacity(requiredDataSize);
        clientMessage.updateFrameLength();
        return clientMessage;
    }

    /**
     * sample data size estimation
     *
     * @return size
     */
    public static int calculateDataSize() {
        return ClientMessage.HEADER_SIZE;
    }
}