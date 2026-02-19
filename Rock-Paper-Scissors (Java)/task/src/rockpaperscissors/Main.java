package rockpaperscissors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static Random random = new Random();

    public static List<String> states = List.of("scissors", "rock", "paper");

    public static void main(String[] args) throws IOException {
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Hello, " + name);
        int score = 0;

        File f = new File("rating.txt");
        if (f.exists() && f.isFile()) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split("\\s");
                    if (data.length >= 2 && data[0].trim().equals(name)) {
                        score = Integer.parseInt(data[1].trim());
                    }
                }
            }
        }

        String[] items = scanner.nextLine().split(",");
        if (items.length > 0) states = List.of(items);
        System.out.println("Okay, let's start");

        while (true) {
            String player = scanner.nextLine();
            if (player.equals("!exit")) {
                System.out.println("Bye!");
                return;
            }
            if (player.equals("!rating")) {
                System.out.println("Your rating: " + score);
            }
            if (!states.contains(player)) {
                System.out.println("Invalid input");
                continue;
            }

            List<String> winningStates = new ArrayList<>();
            int idx = states.indexOf(player);
            for (int i = 0; i < states.size() / 2; i++) {
                winningStates.add(states.get(++idx % states.size()));
            }

            String computer = states.get(random.nextInt(states.size()));

            if (player.equals(computer)) {
                System.out.printf("There is a draw (%s)\n", player);
                score += 50;
            } else if (winningStates.contains(computer)) {
                System.out.printf("Well done. The computer chose %s and failed\n", computer);
                score += 100;
            } else {
                System.out.printf("Loss: Sorry, but the computer chose %s\n", computer);
            }
        }
    }
}
