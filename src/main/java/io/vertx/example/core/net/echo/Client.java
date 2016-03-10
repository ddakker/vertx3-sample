package io.vertx.example.core.net.echo;

import java.util.Date;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.example.util.Runner;

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class Client extends AbstractVerticle {

  // Convenience method so you can run it in your IDE
  public static void main(String[] args) {
    Runner.runExample(Client.class);
  }

  @Override
  public void start() throws Exception {
    vertx.createNetClient().connect(1234, "localhost", res -> {

      if (res.succeeded()) {
        NetSocket socket = res.result();
        socket.handler(RecordParser.newDelimited("\n", buffer -> {
          System.out.println(new Date() + "Net client receiving: " + buffer.toString("UTF-8"));
        }));


        long l = System.currentTimeMillis();

        socket.write("[control] start\n");
        // Now send some data
        for (int i = 0; i < 1000000; i++) {
          String str = "Hello World! " + i + "\n";
          //System.out.println(new Date() + "Net client sending: " + str);
          if (i%10000 == 0) System.out.println("send: " + i);
          socket.write(str);
        }
        socket.write("[control] end\n");
        System.out.println("send end time: " + (System.currentTimeMillis() - l));

        //socket.close();
      } else {
        System.out.println("Failed to connect " + res.cause());
      }
    });
  }
}