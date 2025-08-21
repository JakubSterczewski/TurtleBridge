# Turtle Bridge

This project is a **Java implementation** of a classic arcade-style
game, built as an object-oriented desktop application.\
It is designed not only as a playable game but also as a demonstration
of clean architecture, modularity, and event-driven programming.

------------------------------------------------------------------------

## Showcase

https://github.com/user-attachments/assets/a8d1e0d1-2ca8-4085-9f62-d6e28cfc7952

------------------------------------------------------------------------

## Technical Overview

### Core Concepts

-   **Language**: Java
-   **UI Toolkit**: Swing
-   **Architecture**: Modular design with separation of concerns
    -   **Game logic layer** (`p02.game`)
    -   **Presentation/UI layer** (`p02.pres`)
    -   **Resources** (`p02.assets`)
-   **Event-driven programming model** with custom event interfaces
    (e.g., `StartListener`, `TickListener`, `EndListener`).
-   **Simplified MVC** pattern, where (`p02.game`) acts as the **Model**, (`p02.pres`) as the **View**, and **Controller** responsibilities are distributed between the (`GameThread`) and UI event handlers.

### Key Features

-   Thread-based **game loop** (`GameThread`) controlling state updates
    and rendering.
-   **Board abstraction** (`Board`, `BoardTable`) for maintaining the
    state of the game world.
-   **Custom rendering system** using Swing table models and image
    renderers.
-   **Score system** implemented with a reusable **seven-segment display
    component**.
