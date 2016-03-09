package io.vertx.example.core.net.echo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetSocket;
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
        socket.handler(buffer -> {
          System.out.println("Net client receiving: " + buffer.toString("UTF-8"));
        });

        socket.write("start\n");
        // Now send some data
        for (int i = 0; i < 5; i++) {
          String str = "hello " + i + "\n";
          System.out.println("Net client sending: " + str);
          //if (i%10000 == 0) System.out.println("send: " + i);
          socket.write(str);
        }
        System.out.println("send end");
        socket.write("end\n");
        
        socket.close();
      } else {
        System.out.println("Failed to connect " + res.cause());
      }
    });
  }
}