```mermaid
classDiagram

class ProgressBar {
  <<Interface>>
  ~getContainer() VBox
  ~setProgress(int progress) void
  ~setProgressMaxProgress(int progress, int maxProgress) void
  ~setColor(Color back, Color front) void
  ~setName(String name) void
}

class ProgressBarDecorator {
  <<Abstract>>
  #getDecorated() ProgressBar
  #ProgressBarDecorator(ProgressBar decorated) ProgressBarDecorator
}

ProgressBar <|-- ProgressBarDecorator
