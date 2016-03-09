package io.vertx.example.core.net.echo;

import java.util.Date;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.example.util.Runner;
import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class Server extends AbstractVerticle {

	// Convenience method so you can run it in your IDE
	public static void main(String[] args) {
		Runner.runExample(Server.class);
	}

	@Override
    public void start() throws Exception {
		vertx.createNetServer().connectHandler(socket -> {

			String handlerID = socket.writeHandlerID();

			
			
			socket.handler(RecordParser.newDelimited("\n", buffer -> {
			//socket.handler(buffer -> {

				String message = buffer.toString().trim();

				//System.out.println(socket.remoteAddress() + " (" + handlerID + ") => " + message + "\r\n");
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//socket.write("Receive => " + message + "\r\n");
				if (message.contains("start")) {
					System.out.println("s: " + new Date());
				}
				if (message.contains("end")) {
					System.out.println("e: " + new Date());
				}

			}));

		}).listen(1234);
    }
}