#  Descripton & Data Structure
The Game file contains a class named "Game," which serves as a representation of a baseball game. This class encompasses ten fields that hold essential information, such as the game's date, the season in which it took place, the home team, the away team, the scores achieved by both teams, and various Elo ratings used for predicting the game's outcome. To facilitate JSON serialization and deserialization, the Game class features custom decoder and encoder methods, utilizing the zio.json library. Moreover, the class implements a custom decoder to read data from a JDBC database.

Throughout the code, the Game class interacts with several other objects defined within the system, including HomeTeams, AwayTeams, HomeScores, AwayScores, HomeElos, AwayElos, HomeProbElos, AwayProbElos, GameDates, and SeasonYears


# Installation
``
sbt
project rest
compile 
~ reStart
``

# Usage
The server will be accessible at `http://localhost:8080/`
You can then use the following endpoints: 
- GET /init: Initializes the database.
- GET /game/latests/{{homeTeam}}/{{awayTeam}}: This endpoint allows you to retrieve the latest 20 games played between two specified teams.
- GET /game/predict/{{homeTeam}}/{{awayTeam}}: By utilizing historical data, this endpoint predicts the outcome of a match between the two teams provided.
- GET /team/elo/{{homeTeam}}: Use this endpoint to obtain the Elo rating of a specific team.
- GET /games/{{count}}: This endpoint provides the total count of games stored in the historical data.
- GET /games/history/{{team}}: Retrieve all games related to a particular team from the historical data.
