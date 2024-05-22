# Functional Requirements

## Introduction
Cincy Gas currently receives all propane orders via telephone. The upkeep of having to listen to voicemails and call back is time consuming. Orders are written by hand and stored in boxes, this is also time consuming. This application will begin to address the upkeep and maintenance by digitizing orders and deliveries.

Users should be able to make orders without having to call in.
Users should be able to see their order's delivery status.
Users should be able to be notified of an order's status change.
The owner should be able to see what orders have been placed.
The owner should be able to update order statuses.

## Requirements
1. As a user, I want to place an order for propane tanks online, so that I can receive propane at my specified address without calling.
   
**Given**: the user is on the order page and provides valid details.
**When**: the user fills in their name, delivery address, phone number, selects tank sizes and quantities, and submits the form.
**Then**: the order should be successfully placed, and the user should receive a confirmation message.

**Given**: the user is on the order page.
**When**: the user leaves required fields (name, address, phone number) empty and tries to submit the form.
**Then**: the system should display an error message indicating the required fields need to be filled.

**Given**: the user is on the order page.
**When**: the user enters an invalid phone number or email format and tries to submit the form.
**Then**: the system should display an error message indicating the correct format is needed.

2. As a user, I want to view my order details after placing an order, so that I can verify the order information and delivery status.
**Given**: the user has placed an order.
**When**: the user navigates to the order history page and selects an order.
**Then**: the system should display the order details including customer information, tank sizes, quantities, order date, and delivery status.

**Given**: the user has placed multiple orders.
**When**: the user navigates to the order history page.
**Then**: the system should display a list of all orders, and the user can select any order to view its details.

3. As a user, I want to receive updates on my order status, so that I know when to expect my delivery.
**Given**: the user has placed an order and provided an email address.
**When**: the order status changes (e.g., confirmed, out for delivery, delivered).
**Then**: the system should send an email notification to the user with the updated status.

**Given**: the user has placed an order but provided an invalid email address.
**When**: the order status changes.
**Then**: the system should log an error and attempt to notify the user via phone if possible.

4. As a user, I want to update my order before it is confirmed, so that I can make changes to the delivery details or tank quantities if needed.
**Given**: the user has placed an order but it is not yet confirmed.
**When**: the user navigates to the order details page and edits the information.
**Then**: the system should allow the changes and update the order details.

**Given**: the user has placed an order and it has been confirmed.
**When**: the user tries to edit the order details.
**Then**: the system should display a message indicating that the order cannot be updated after confirmation.

5. As an owner, I want to update order statuses, so that I can notify my users without calling.
**Given**: I have delivered the tank to the delivery address.
**When**: I navigate to the order and change the order's delivery status.
**Then**: the system should update the order details and notify the user.
