# Project Activity 1

The topic of my project is **Public Transportation Ticketing System**.

## Business Agents

- Validation System
- Transport Information System

## Business Actors

- Passenger

## Business Activities

- Buy tickets/travel cards
- Create an account
- Top-up travel cards
- Plan journey
- Search optimal routes
- Receive travel notifications
- Display arrival/departure information
- Validate ticket
- View real-time transport information

## Business Processes
- **Account Management**:
  - *(Optionally)* Passenger creates an account
  - Passenger logs in
  - Passenger manages travel card
  - Passenger tops up balance
- **Ticket Purchase**:
  - Passenger chooses where to buy a ticket
  - Passenger selects ticket type
  - System processes payment
  - System issues a physical/digital ticket
- **Travel Planning**:
  - Passenger enters a starting point and destination
  - System retrieves real-time transport information
  - System calculates optimal routes
  - System outputs journey options
- **Ticket Validation**:
  - Passenger taps travel card at validator 
  - System checks validity
  - System confirms or rejects entry
- **Travel Information Update**:
  - System receives real-time transport data
  - System updates arrival/departure time
  - Information is displayed on information screens at stations or in mobile app

## Business Rules

| Code     | Rule    |
| -------- | ------- |
| BR1      | Passengers must be allowed to purchase tickets for single journeys, daily passes, or monthly travelcards. |
| BR2      | Ticket purchases must be available through self-service kiosks, mobile apps, or the official website. |
| BR3      | Passengers must be able to create personal accounts, and to manage them. |
| BR4      | Passengers must be able to view estimated travel times, and find optimal routes using various modes of public transport. |
| BR5      | Passengers must validate their travel cards or tickets on designated readers at entry and exit points. |
| BR6      | Passengers must be able to have an up-to-date public transport arrivals and departures. |
| BR7      | Passengers must be notified about any potential delays and any service disruptions in real time.|
| BR8      | The system must provide secure online payments through integrated payment gateways.|

## Business Use Case Diagram

![Business Use Case Diagram](bucd.png)

```plantuml
@startuml
left to right direction

:Passenger: as p


rectangle "Public Transport Ticketing System" {
 usecase "Create account" as UC0
 usecase "Top-up travel card" as UC1
 usecase "Purchase ticket" as UC2
 usecase "Validate ticket" as UC5
 usecase "Plan journey" as UC6
 usecase "View real-time transport info" as UC7
 usecase "Receive notifications" as UC8
 usecase "Process payment" as UC9

:Validation System: as VS
:Transport Information System: as TIS
 
 UC2 ..> UC9 : <<include>>
 UC1 ..> UC9 : <<include>>
}

p -- UC0
p -- UC1
p -- UC2
p -- UC5
p -- UC6
p -- UC7
p -- UC8

UC5 -- VS
UC6 -- TIS
UC7 -- TIS
UC8 -- TIS
@enduml
```

## Activity Diagram

![activity_diagram](activity_diagram.png)

```plantuml
@startuml
|Passenger|
start
:Access System;

split
   :Create Account;
   :Enter Personal Details;
split again
   :Log In;
split again
   :Continue as Guest;
end split

partition "Travel Planning (BR4, BR6, BR7)" {
    :Enter Origin & Destination;
    |Transport Information System|
    :Retrieve Real-time Data;
    :Calculate Optimal Routes;
    :Display Arrival/Departure Info;
    |Passenger|
    :Select Journey;
}

partition "Ticket Purchase & Payment (BR1, BR2, BR8)" {
    :Select Ticket Type;
    note right: Single, Daily, or Monthly
    :Initiate Payment;
    |Transport Information System|
    :Process Secure Payment;
    if (Payment Successful?) then (yes)
        :Issue Digital/Physical Ticket;
        :Update Account/Card Balance;
    else (no)
        :Display Payment Error;
        detach
    endif
}

partition "Validation (BR5)" {
    |Passenger|
    :Tap Travel Card/Ticket at Validator;
    |Validation System|
    :Check Validity & Funds;
    if (Ticket Valid?) then (yes)
        :Confirm Entry;
        :Open Turnstile/Display Green;
    else (no)
        :Reject Entry;
        :Display Error Message;
        stop
    endif
}

|Passenger|
:Complete Journey;
stop
@enduml
```

## Domain Model

![domain_model](domain_model.png)

```plantuml
@startuml
skinparam linetype ortho
hide empty methods

class Passenger {
    + name
    + email
    + password
}

class Account {
    + accountID
    + balance
    + creationDate
}

class TravelCard {
    + cardID
    + status
    + balance
}

class Ticket {
    + ticketID
    + type
    + price
    + issueDate
    + expiryDate
}

class JourneyPlan {
    + origin
    + destination
    + estimatedTravelTime
}

class Route {
    + routeID
    + modeOfTransport
    + schedule
}

class PaymentTransaction {
    + transactionID
    + amount
    + timestamp
    + status
}

class Notification {
    + message
    + timestamp
    + type
}

class Validator {
    + deviceID
    + location
}

' Relationships
Passenger "1" -- "0..1" Account : owns >
Account "1" *-- "0..*" TravelCard : manages >
Passenger "1" -- "0..*" Ticket : purchases >
Passenger "1" -- "0..*" JourneyPlan : creates >

JourneyPlan "1" o-- "1..*" Route : consists of >
Ticket "0..*" -- "1" PaymentTransaction : paid via >
TravelCard "0..*" -- "1" PaymentTransaction : topped up via >

Passenger "1" -- "0..*" Notification : receives >
Ticket "1" -- "0..*" Validator : validated at >
TravelCard "1" -- "0..*" Validator : validated at >

@enduml
```