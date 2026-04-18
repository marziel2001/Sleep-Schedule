# Sleep-Schedule

A Java utility that analyzes a weekly meeting schedule and calculates the **longest possible uninterrupted sleep window** (in minutes) across the entire week.

---

## 📖 About

Given a list of meetings spread across a 7-day week, the app finds the largest continuous gap between meetings — the ideal window for an uninterrupted night's (or day's) sleep. Gaps that span midnight and carry over into the next day are handled correctly, so a sleep window can stretch across day boundaries.

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java (JDK 8+) |
| Date/Time | `java.time.LocalTime`, `java.time.temporal.ChronoUnit` |
| Collections | `java.util.ArrayList`, `java.util.Collections` |
| Build | Plain `javac` / any Java IDE |

No external libraries or build tools are required.

---

## 🏗️ Architecture

The project is a single-file solution (`src/Solution.java`) structured as follows:

```
Solution
├── DaysEnum            – Enum mapping day abbreviations (Mon–Sun) to indices 0–6
├── getData(String)     – Parses the raw schedule string into a sorted array of
│                         per-day meeting time ranges (ArrayList<LocalTime[]>[7])
├── solution(String)    – Iterates over each day, accumulates free-time streaks
│                         between meetings, and returns the maximum streak
└── main(String[])      – Entry point with a hard-coded example schedule
```

**Algorithm overview:**
1. Parse each `"Day HH:MM-HH:MM"` line into `LocalTime` pairs.
2. Sort meetings within each day by start time.
3. Walk through all 7 days, tracking the running free-time streak — including time left over at the end of one day and carried into the start of the next.
4. Return the largest streak found.

---

## 🚀 How to Use

### 1. Compile

```bash
cd src
javac Solution.java
```

### 2. Run

```bash
java Solution
```

The `main` method contains a sample schedule. Modify the `in` string to use your own schedule.

### 3. Input format

Each line represents one meeting:

```
<Day> <HH:MM>-<HH:MM>
```

- **Day** — three-letter abbreviation: `Mon`, `Tue`, `Wed`, `Thu`, `Fri`, `Sat`, `Sun`
- **Time range** — 24-hour format, e.g. `09:00-10:30`
- Multiple meetings on the same day are supported; they will be sorted automatically.
- `24:00` is treated as `23:59`.

**Example input:**

```
Mon 09:00-10:00
Mon 14:00-15:30
Wed 08:00-09:00
Fri 22:00-23:30
```

### 4. Output

A single integer — the length of the longest uninterrupted free window **in minutes**.

---

## 📂 Project Structure

```
Sleep-Schedule/
└── src/
    └── Solution.java   # Full solution (parsing + algorithm + entry point)
```

