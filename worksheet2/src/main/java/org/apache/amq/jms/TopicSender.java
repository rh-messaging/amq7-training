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
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

/**
 * @author <a href="mailto:andy.taylor@jboss.org">Andy Taylor</a>
 */
public class TopicSender {
   public static void main(String[] args) throws Exception {

      InitialContext initialContext = new InitialContext();
      Topic topic = (Topic) initialContext.lookup("topic/exampleTopic");
      ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
      try
      (
         Connection connection = cf.createConnection();
         Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      )
      {

         MessageProducer producer = session.createProducer(topic);

         TextMessage message_1 = session.createTextMessage("this is the 1st message");
         TextMessage message_2 = session.createTextMessage("this is the 2nd message");
         TextMessage message_3 = session.createTextMessage("this is the 3rd message");
         TextMessage message_4 = session.createTextMessage("this is the 4th message");
         TextMessage message_5 = session.createTextMessage("this is the 5th message");
         TextMessage message_6 = session.createTextMessage("this is the 6th message");
         TextMessage message_7 = session.createTextMessage("this is the 7th message");
         TextMessage message_8 = session.createTextMessage("this is the 8th message");
         TextMessage message_9 = session.createTextMessage("this is the 9th message");
         TextMessage message_10 = session.createTextMessage("this is the 10th message");

         producer.send(message_1);
         producer.send(message_2);
         producer.send(message_3);
         producer.send(message_4);
         producer.send(message_5);
         producer.send(message_6);
         producer.send(message_7);
         producer.send(message_8);
         producer.send(message_9);
         producer.send(message_10);
         System.out.println("10 messages sent");
         System.exit(0);
      }
   }
}
