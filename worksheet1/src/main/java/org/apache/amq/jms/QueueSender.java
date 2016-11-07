/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.amq.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author <a href="mailto:andy.taylor@jboss.org">Andy Taylor</a>
 */
public class QueueSender {
   public static void main(String[] args) throws Exception {

      InitialContext initialContext = new InitialContext();
      Queue queue = (Queue) initialContext.lookup("queue/exampleQueue");
      ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
      try
      (
            Connection connection = cf.createConnection("myuser", "mypassword");
         Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      )
      {

         // Step 6. Create a JMS Message Producer
         MessageProducer producer = session.createProducer(queue);

         // Step 7. Create 2 Text Messages
         TextMessage message_1 = session.createTextMessage("this is the 1st message");
         TextMessage message_2 = session.createTextMessage("this is the 2nd message");

         producer.send(message_1);
         producer.send(message_2);
         System.out.println("2 messages sent");
         System.exit(0);
      }
   }
}
