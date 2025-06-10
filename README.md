## Gaming Trading System

The system should simulate a gamified trading feature for an investment app and allow users to:
1. Create portfolios and add assets.
2. Simulate trading of assets (buy/sell).
3. Earn gems based on trading activities.
4. Introduce a ranking system that tracks users with the highest gem count.

### Rewards System

#### Base Rewards
- 1 gem per trade (buy or sell)

#### Milestone Bonuses
- 5 bonus gems at 5 trades
- 10 bonus gems at 10 trades
- Bonus gems of trade count at every 20 trades thereafter (20, 40, etc.)

#### Streak Bonuses
- 3 gems for every consecutive 3 trades, reset if trade is performed by another user
- Bonus gems equal to streak count when streak ≥ 7

### Running the Application
This application is built using Spring Boot 3.5.0 and java 24.

```sh
# Clone the repository
git clone https://github.com/TPriime/tradesys.git

# Navigate to project directory
cd tradesys

# Run the application
./gradlew bootRun
```
The application should now be available at:
http://localhost:8080

### 📌 **API Endpoints**
| Method | Endpoint                      | Description             |
|--------|-------------------------------|-------------------------|
| `GET`  | `/api/assets`                 | Fetch all assets        |
| `GET`  | `/api/trades`                 | Fetch trades all trades |
| `GET`  | `/api/trades?userId={userId}` | Fetch trades by user    |
| `GET`  | `/api/users`                  | Fetch all uers          |
| `GET`  | `/api/users/{userId}`         | Fetch a user            |
| `GET`  | `/api/users/leaderboard`      | Fetch top ranking users |
| `GET`  | `/api/users/1/stats`          | Fetch user stats        |
| `POST` | `/api/trades/buy`             | Perform trade           |
| `POST` | `/api/users`                  | Create a user           |

### Common POST Request Examples

#### Create User
Creates a user with an initial fund of 1000.00 units for trading activities.
```json
POST /api/users
{
    "username": "username"
}
```

#### Execute Trade
```json
POST /api/trade
{
  "userId": 1,
  "assetId": 1,
  "quantity": "2.65"
}
```