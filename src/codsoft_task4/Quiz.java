package codsoft_task4;


import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Quiz {
    private final String participantName;
    private final Question[] questions;
    private final int[] participantAnswers;
    private int participantScore;
    private final int timePerQuestion;

    public Quiz(String participantName, Question[] questions, int timePerQuestion) {
        this.participantName = participantName;
        this.questions = questions;
        this.participantAnswers = new int[questions.length];
        this.timePerQuestion = timePerQuestion;
        this.participantScore = 0;
    }

    public void beginQuiz() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Epic Quiz Challenge, " + participantName + "!");
        System.out.println("You will have " + timePerQuestion + " seconds to answer each question.");
        System.out.println("Let's get started!\n");

        for (int i = 0; i < questions.length; i++) {
            Question question = questions[i];
            System.out.println("Question " + (i + 1) + ": " + question.retrieveQuestion());
            String[] options = question.retrieveOptions();
            for (int j = 0; j < options.length; j++) {
                System.out.println((j + 1) + ": " + options[j]);
            }

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("\nTime's up, " + participantName + "!");
                    System.exit(0);
                }
            };
            timer.schedule(task, timePerQuestion * 1000L); // Schedule the timer task to run after the specified timePerQuestion in milliseconds


            int answer = -1;
            boolean validAnswer = false;
            while (!validAnswer) {
                try {
                    System.out.print("Your answer: ");
                    answer = sc.nextInt();
                    if (answer >= 1 && answer <= options.length) {
                        validAnswer = true;
                    } else {
                        System.out.println("Invalid answer. Please enter a number between 1 and " + options.length + ".");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.next(); // Clear invalid input
                }
            }

            participantAnswers[i] = answer - 1; // Store the participant's answer
            timer.cancel();

            if (question.isCorrect(answer - 1)) {
                participantScore++;
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect. The correct answer was: " + options[question.retrieveCorrectOptionIndex()]);
            }

            System.out.println();
        }

        // Display Quiz is Over
        System.out.println("Quiz Challenge is over! \n");

        // Display the final score
        System.out.println(participantName + ", your final score is: " + participantScore + "/" + questions.length);

        // Display the summary of the quiz
        System.out.println("\nSummary of the Quiz: \n");
        for (int i = 0; i < questions.length; i++) {
            Question question = questions[i];
            System.out.println("Question " + (i + 1) + ": " + question.retrieveQuestion());
            System.out.println("Your answer: " + (participantAnswers[i] + 1));
            System.out.println("Correct answer: " + (question.retrieveCorrectOptionIndex() + 1));
            if (question.isCorrect(participantAnswers[i])) {
                System.out.println("Result: Correct\n");
            } else {
                System.out.println("Result: Incorrect\n");
            }
        }

        sc.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String participantName = scanner.nextLine();

        Question[] questions = {
                new Question("Which city hosted the 2023 ICC Men's Cricket World Cup?", new String[]{"Melbourne", "Ahmedabad", "London", "Bengaluru"}, 1),
                new Question("Which Indian city was awarded the UNESCO World Heritage status in 2023?", new String[]{"Jaipur", "Ahmedabad", "Varanasi", "Shantiniketan"}, 3),
                new Question("India hosted the G20 Summit in which year?", new String[]{"2020", "2021", "2022", "2023"}, 3),
                new Question("Who is the current Prime Minister of India as of 2024?", new String[]{"Rahul Gandhi", "Narendra Modi", "Arvind Kejriwal", "Amit Shah"}, 1),
                new Question("Which country successfully landed a rover on the Moon's South Pole in 2023?", new String[]{"India", "China", "USA", "Russia"}, 0),
                new Question("Which of the following stars is nearest to the sun?", new String[]{"Sirius", "Deneb", "Proxima Centauri", "Betelgeuse"}, 2),
                new Question("What is the name of SpaceX's spacecraft that is being developed for Mars missions?", new String[]{"Falcon9", "Starship", "Dragon", "Blue Origin"}, 1),
                new Question("Which country will host the 2024 Summer Olympics?", new String[]{"USA", "India", "Japan", "Brazil"}, 2),
                new Question("What is the name of the weak zone of the earth's crust?", new String[]{"Seismic", "Cosmic", "Formic", "Anaemic"}, 0),
                new Question("The green planet in the solar system is?", new String[]{"Mars", "Uranus", "Venus", "Earth"}, 1)
        };

        Quiz personalizedQuiz = new Quiz(participantName, questions, 15); // 15 seconds per question
        personalizedQuiz.beginQuiz();
        scanner.close();
    }
}

class Question {
    private final String question;
    private final String[] options;
    private final int correctOption;

    public Question(String question, String[] options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String retrieveQuestion() {
        return question;
    }

    public String[] retrieveOptions() {
        return options;
    }

    public int retrieveCorrectOptionIndex() {
        return correctOption;
    }

    public boolean isCorrect(int answer) {
        return answer == correctOption;
    }
}
