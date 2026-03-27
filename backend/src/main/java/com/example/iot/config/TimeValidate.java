package com.example.iot.config;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.regex.Pattern;

@Component
public class TimeValidate {

    public record TimeRange(LocalDateTime start, LocalDateTime end) {}

    private static final Pattern YEAR              = Pattern.compile("^\\d{4}$");
    private static final Pattern YEAR_MONTH        = Pattern.compile("^\\d{4}/\\d{2}$");
    private static final Pattern YEAR_MONTH_DAY    = Pattern.compile("^\\d{4}/\\d{2}/\\d{2}$");
    private static final Pattern YEAR_MONTH_DAY_H  = Pattern.compile("^\\d{4}/\\d{2}/\\d{2} \\d{2}$");
    private static final Pattern YEAR_MONTH_DAY_HM = Pattern.compile("^\\d{4}/\\d{2}/\\d{2} \\d{2}/\\d{2}$");
    private static final Pattern YEAR_MONTH_DAY_HMS= Pattern.compile("^\\d{4}/\\d{2}/\\d{2} \\d{2}/\\d{2}/\\d{2}$");

    public static TimeRange parse(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Time input must not be null or blank");
        }

        String s = input.trim();

        if (YEAR.matcher(s).matches()) {
            int year = Integer.parseInt(s);
            return new TimeRange(
                    LocalDateTime.of(year, 1, 1, 0, 0, 0),
                    LocalDateTime.of(year, 12, 31, 23, 59, 59)
            );
        }

        if (YEAR_MONTH.matcher(s).matches()) {
            String[] p = s.split("/");
            int year = Integer.parseInt(p[0]);
            int month = Integer.parseInt(p[1]);
            int lastDay = YearMonth.of(year, month).lengthOfMonth();
            return new TimeRange(
                    LocalDateTime.of(year, month, 1, 0, 0, 0),
                    LocalDateTime.of(year, month, lastDay, 23, 59, 59)
            );
        }

        if (YEAR_MONTH_DAY.matcher(s).matches()) {
            String[] p = s.split("/");
            int year = Integer.parseInt(p[0]);
            int month = Integer.parseInt(p[1]);
            int day = Integer.parseInt(p[2]);
            return new TimeRange(
                    LocalDateTime.of(year, month, day, 0, 0, 0),
                    LocalDateTime.of(year, month, day, 23, 59, 59)
            );
        }

        if (YEAR_MONTH_DAY_H.matcher(s).matches()) {
            String[] parts = s.split(" ");
            String[] date = parts[0].split("/");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            int hour = Integer.parseInt(parts[1]);
            return new TimeRange(
                    LocalDateTime.of(year, month, day, hour, 0, 0),
                    LocalDateTime.of(year, month, day, hour, 59, 59)
            );
        }

        if (YEAR_MONTH_DAY_HM.matcher(s).matches()) {
            String[] parts = s.split(" ");
            String[] date = parts[0].split("/");
            String[] time = parts[1].split("/");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);
            return new TimeRange(
                    LocalDateTime.of(year, month, day, hour, minute, 0),
                    LocalDateTime.of(year, month, day, hour, minute, 59)
            );
        }

        if (YEAR_MONTH_DAY_HMS.matcher(s).matches()) {
            String[] parts = s.split(" ");
            String[] date = parts[0].split("/");
            String[] time = parts[1].split("/");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);
            int second = Integer.parseInt(time[2]);
            LocalDateTime dt = LocalDateTime.of(year, month, day, hour, minute, second);
            return new TimeRange(dt, dt);
        }

        throw new IllegalArgumentException(
                "Invalid time format: '" + input + "'. Accepted: YYYY | YYYY/MM | YYYY/MM/DD | YYYY/MM/DD HH | YYYY/MM/DD HH/MM | YYYY/MM/DD HH/MM/SS"
        );
    }
}