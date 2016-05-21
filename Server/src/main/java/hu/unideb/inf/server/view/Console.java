package hu.unideb.inf.server.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

import hu.unideb.inf.server.Main;
import hu.unideb.inf.server.clientConnector.ClientService;

public class Console {

	private ClientService clientService;

	public Console(ClientService clientService) {
		this.clientService = clientService;
	}

	LocalDateTime nextDraw;

	Runnable nextDrawTimer = new Runnable() {
		public void run() {
			while(nextDraw != null) {
				if(nextDraw.isBefore(LocalDateTime.now())){
					draw();
					nextDraw = null;
				} else 
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {}
			}
		}
	};

	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String command = null;
		while (true) {
			try {
				command = reader.readLine().toLowerCase();
				if (command.contains("start")) {
					start();
				} else if (command.contains("stop")) {
					stop();
					break;
				} else if (command.startsWith("draw")) {
					draw();
				} else if (command.startsWith("nextdraw")) {
					nextDraw();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void start() {
		if (clientService.isRunning())
			System.out.println("Már fut a szerver\n");
		else
			clientService.startServer();
	}

	private void stop() {
		nextDraw = null;
		if (Main.clientService.isRunning())
			Main.clientService.stopServer();
		else
			System.out.println("A szerver nem fut\n");
	}

	private void draw() {
		if(!Main.clientService.isRunning()){
			System.out.println("Nem fut a szerver");
			return;
		}
		if (Main.clientService.isRunning())
			Main.drawer.draw();
	}

	private void nextDraw() throws IOException {
		if(!Main.clientService.isRunning()){
			System.out.println("Nem fut a szerver");
			return;
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String[] units = {"másodperc", "perc", "óra", "nap"};
		int unit = 0;
		int quantity = 0;
		System.out.println("Válassz egy egységet:\n\t1: másodperc\n\t2: perc\n\t3: óra\n\t4: nap");
		while(unit <1 || unit > 4) {
			unit = Integer.parseInt(reader.readLine());
			System.out.println(unit);
		}
		System.out.println("Hány " + units[unit-1] + " múlva legyen a következő sorsolás?");
		while(quantity < 1)
			quantity = Integer.parseInt(reader.readLine());
		
		switch(unit) {
		case 1:
			nextDraw = LocalDateTime.now().plusSeconds(quantity);
			nextDrawTimer.run();
			break;
		case 2:
			nextDraw = LocalDateTime.now().plusMinutes(quantity);
			nextDrawTimer.run();
			break;
		case 3:
			nextDraw = LocalDateTime.now().plusHours(quantity);
			nextDrawTimer.run();
			break;
		case 4:
			nextDraw = LocalDateTime.now().plusDays(quantity);
			nextDrawTimer.run();
			break;
		}
	}
}