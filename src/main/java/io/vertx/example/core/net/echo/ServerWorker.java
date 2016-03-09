package io.vertx.example.core.net.echo;

import java.util.Date;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.example.util.Runner;

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class ServerWorker extends AbstractVerticle {

	// Convenience method so you can run it in your IDE
	public static void main(String[] args) {
		Runner.runExample(ServerWorker.class);
	}

	@Override
	public void start() throws Exception {
		vertx.deployVerticle("io.vertx.example.core.verticle.worker.WorkerVerticle",
				new DeploymentOptions().setWorker(true).setInstances(2));

		vertx.createNetServer().connectHandler(socket -> {

			String handlerID = socket.writeHandlerID();

			socket.handler(RecordParser.newDelimited("\n", buffer -> {
				// socket.handler(buffer -> {

				String message = buffer.toString().trim();

				vertx.eventBus().send("sample.data", message, r -> {
					System.out.println("[Main] Receiving reply ' " + r.result().body() + "' in " + Thread.currentThread().getName());
					socket.write("Receive => " + message + "\n");
				});

			}));

		}).listen(1234);
	}
}