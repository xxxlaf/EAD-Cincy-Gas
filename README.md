# Functional Requirements

## Introduction
Cincy Gas currently receives all propane orders via telephone. The upkeep of having to listen to voicemails and call back is time consuming. Orders are written by hand and stored in boxes, this is also time consuming. This application will begin to address the upkeep and maintenance by digitizing orders and deliveries.

Users should be able to make orders without having to call in.
Users should be able to see their order's delivery status.
Users should be able to be notified of an order's status change.
The owner should be able to see what orders have been placed.
The owner should be able to update order statuses.

## Storyboard
![Order Propane Tanks](https://github.com/xxxlaf/EAD-Cincy-Gas/assets/40042446/13c95b11-cbda-4912-9d45-0d4875b29ffc)
![Order Confirmation](https://github.com/xxxlaf/EAD-Cincy-Gas/assets/40042446/decd528d-babf-4f39-a7db-60bdcd659cdf)
![Order Lookup](https://github.com/xxxlaf/EAD-Cincy-Gas/assets/40042446/de9a59fe-8d6e-40dd-8c0d-b59f83eba6e9)

## Requirements
1. As a user, I want to place an order for propane tanks online, so that I can receive propane at my specified address without calling.
<br>
<ul>
  <li><strong>Given</strong>: the user is on the order page and provides valid details.</li>
  <li><strong>When</strong>: the user fills in their name, delivery address, phone number, selects tank sizes and quantities, and submits the form.</li>
  <li><strong>Then</strong>: the order should be successfully placed, and the user should receive a confirmation message.</li>
</ul>
<br>
<ul>
  <li><strong>Given</strong>: the user is on the order page.</li>
  <li><strong>When</strong>: the user leaves required fields (name, address, phone number) empty and tries to submit the form.</li>
  <li><strong>Then</strong>: the system should display an error message indicating the required fields need to be filled.</li>
</ul>
<br>
<ul>
  <li><strong>Given</strong>: the user is on the order page.</li>
  <li><strong>When</strong>: the user enters an invalid phone number or email format and tries to submit the form.</li>
  <li><strong>Then</strong>: the system should display an error message indicating the correct format is needed.</li>
</ul>
<br>
2. As a user, I want to view my order details after placing an order, so that I can verify the order information and delivery status.
<br>
<ul>
  <li><strong>Given</strong>: the user has placed an order.</li>
  <li><strong>When</strong>: the user navigates to the order history page and selects an order.</li>
  <li><strong>Then</strong>: the system should display the order details including customer information, tank sizes, quantities, order date, and delivery status.</li>
</ul>
<br>
<ul>
  <li><strong>Given</strong>: the user has placed multiple orders.</li>
  <li><strong>When</strong>: the user navigates to the order history page.</li>
  <li><strong>Then</strong>: the system should display a list of all orders, and the user can select any order to view its details.</li>
</ul>
<br>
3. As a user, I want to receive updates on my order status, so that I know when to expect my delivery.
<br>
<ul>
  <li><strong>Given</strong>: the user has placed an order and provided an email address.</li>
  <li><strong>When</strong>: the order status changes (e.g., confirmed, out for delivery, delivered).</li>
  <li><strong>Then</strong>: the system should send an email notification to the user with the updated status.</li>
</ul>
<br>
<ul>
  <li><strong>Given</strong>: the user has placed an order but provided an invalid email address.</li>
  <li><strong>When</strong>: the order status changes.</li>
  <li><strong>Then</strong>: the system should log an error and attempt to notify the user via phone if possible.</li>
</ul>
<br>
4. As a user, I want to update my order before it is confirmed, so that I can make changes to the delivery details or tank quantities if needed.
<br>
<ul>
  <li><strong>Given</strong>: the user has placed an order but it is not yet confirmed.</li>
  <li><strong>When</strong>: the user navigates to the order details page and edits the information.</li>
  <li><strong>Then</strong>: the system should allow the changes and update the order details.</li>
</ul>
<br>
<ul>
  <li><strong>Given</strong>: the user has placed an order and it has been confirmed.</li>
  <li><strong>When</strong>: the user tries to edit the order details.</li>
  <li><strong>Then</strong>: the system should display a message indicating that the order cannot be updated after confirmation.</li>
</ul>
<br>
5. As an owner, I want to update order statuses, so that I can notify my users without calling.
<br>
<ul>
  <li><strong>Given</strong>: I have delivered the tank to the delivery address.</li>
  <li><strong>When</strong>: I navigate to the order and change the order's delivery status.</li>
  <li><strong>Then</strong>: the system should update the order details and notify the user.</li>
</ul>

## Class Diagram

![Cincy Gas Class Diagram](https://github.com/xxxlaf/EAD-Cincy-Gas/assets/40042446/99617bfb-1a1a-444e-b6d7-ebe6da9c2b5e)

### Class Diagram Details
1. **Customer**: contains customer details and has a method to place an order.
2. **Order**: manages orders, holds a list of propane tanks, calculates the total number of tanks, and confirms the order.
3. **Propane Tank**: represents individual propane tanks with their size and quantity.
4. **Delivery**: manages the delivery status details for each order, schedules deliveries, and updates their status.

### JSON Schema
**Customer**: 
>{
>    "type": "object",
>    "properties": {
>        "customerId": {
>            "type": "integer"
>        },
>        "fulLName": {
>            "type": "string"
>        },
>        "deliveryAddress": {
>            "type": "string"
>        },
>        "companyName": {
>            "type": "string"
>        },
>        "phoneNumber": {
>            "type": "string"
>        },
>        "emailAddress": {
>            "type": "string"
>        }
>    }
>}

**Order**: 
>{
>    "type": "object",
>    "properties": {
>        "orderId": {
>            "type": "integer"
>        },
>        "customerId": {
>            "type": "integer"
>        },
>        "orderDate": {
>            "type": "date"
>        },
>        "listOfTanks": {
>            "type": "array"
>        }
>    }
>}

**Propane Tank**: 
>{
>    "type": "object",
>    "properties": {
>        "tankSize": {
>            "type": "integer"
>        },
>        "quantity": {
>            "type": "integer"
>        }
>    }
>}

**Delivery**: 
>{
>    "type": "object",
>    "properties": {
>        "deliveryId": {
>            "type": "integer"
>        },
>        "orderId": {
>            "type": "string"
>        },
>        "deliveryDate": {
>            "type": "date"
>        },
>        "deliveryStatusId": {
>            "type": "integer"
>        }
>    }
>}

## Scrum Roles
**UI Specialist**: Kayla Neely <br>
**Business Logic and Persistence Specialist**: Cameron Gordon <br>
**Product Owner/Scrum Master/DevOps/GitHub Administrator**: Daniel Waters <br>

## Stand Up Meeting
We chose to use Discord as our tool for the stand up meeting ([link to meeting](https://discord.gg/AGwqKuFz?event=1248017094188990538)).
