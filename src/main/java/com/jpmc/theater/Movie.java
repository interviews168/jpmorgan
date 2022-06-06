package com.jpmc.theater;

import java.time.Duration;
import java.util.Objects;

public class Movie {
    private static int MOVIE_CODE_SPECIAL = 1;

    private String title;
    private String description;
    private Duration runningTime;
    private double ticketPrice;
    private int specialCode;

    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public double calculateTicketPrice(Showing showing) {
        return ticketPrice - getDiscount(showing.getSequenceOfTheDay(), showing.getStartTime());
    }

    private double getDiscount(int showSequence, LocalDateTime showStartTime) {
        double maxDiscount = 0;
        if (showStartTime >= 11 && showStartTime <= 16) {
            maxDiscount = ticketprice * 0.25;
        }
        else if (MOVIE_CODE_SPECIAL == specialCode) {
            maxDiscount = ticketPrice * 0.2;  // 20% discount for special movie
        }

        if (maxDiscount < 3) {
            if (showSequence == 1) {
                maxDiscount = 3; // $3 discount for 1st show
            } else if (showSequence == 2) {
                maxDiscount = 2; // $2 discount for 2nd show
            } else if (showSequence == 7) {
                maxDiscount = 1; // $1 discount for 7th show
            }   
        }

        // biggest discount wins
        return maxDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
    }
}