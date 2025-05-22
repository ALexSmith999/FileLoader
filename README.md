Project Overview
This simple program is designed to solve a specific use case.

We receive files via an external interface. These files come in various predefined formats and contain a series of fields representing accumulated results. Each result is associated with a particular ID and may be added or modified on a specific date (e.g., adjusted, corrected, etc.).

Key Components
Watcher
Monitors a defined directory for new files.

Loader
Saves parsed results into a database.

Modular Loading
Each file type has its own parser, saver, and batch processor.

Design Patterns Used

Abstract Factory Method: Defines the specific components (parser, loader, etc.) required per file type.

Strategy Pattern: Determines the file loading algorithm.

Singleton: Manages a single shared database connection.

Chain of Responsibility: Delegates file handling to the appropriate processor based on file type.

database Interaction
Uses basic PreparedStatement operations (INSERT, UPDATE, SELECT) without dependency injection.
Data type conversion and normalization are currently omitted for simplicity.

Extensibility
Parsers, loaders, and checkers are simplified but structured for easy extension. The design supports adding new file types without breaking SOLID principles.

TODO
Watch a directory tree structure (recursive monitoring).

Use connection pools for more efficient database interaction.

Refactor parsers for better modularity and reuse.

Extend loaders for more advanced processing and batching.
