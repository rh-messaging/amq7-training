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
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.xml.soap.Text;
import java.util.Hashtable;

/**
 * @author <a href="mailto:andy.taylor@jboss.org">Andy Taylor</a>
 */
public class QueueReceiver {
   public static void main(String[] args) throws Exception {
      String port = args[0];
      Hashtable<Object, Object> environment = new Hashtable<>();
      environment.put("java.naming.factory.initial", "org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory");
      environment.put("connectionFactory.ConnectionFactory", "tcp://localhost:" + port);
      environment.put("queue.queue/exampleQueue", "exampleQueue");
      InitialContext initialContext = new InitialContext(environment);
      Queue queue = (Queue) initialContext.lookup("queue/exampleQueue");
      ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
      try
      (
         Connection connection = cf.createConnection();
         Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      )
      {
         connection.start();
         MessageConsumer consumer = session.createConsumer(queue);
         System.out.println("Awaiting message");
         TextMessage message = (TextMessage) consumer.receive();
         System.out.println("message = " + message.getText());
         while((message=(TextMessage) consumer.receiveNoWait())!=null){
            System.out.println("message = " + message.getText());
         }
      }
   }
}
