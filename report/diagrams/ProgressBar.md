```mermaid
classDiagram

class ProgressBar {
  <<interface>>
  ~getContainer() VBox
  ~setProgress(int progress) void
  ~setProgressMaxProgress(int progress, int maxProgress) void
  ~setColor(Color back, Color front) void
  ~setName(String name) void
}
