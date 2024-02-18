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
  ~getContainer() VBox
  ~setProgress(int progress) void
  ~setProgressMaxProgress(int progress, int maxProgress) void
  ~setColor(Color back, Color front) void
  ~setName(String name) void
}

class ProgressBarImpl {
  <<Default>>
  ~getContainer() VBox
  ~setProgress(int progress) void
  ~setProgressMaxProgress(int progress, int maxProgress) void
  ~setColor(Color back, Color front) void
  ~setName(String name) void
}

class HealthBar {
  <<Default>>
  #HealthBar(ProgressBar b) HealthBar
}

class ExperienceBar {
  <<Default>>
  #ExperienceBar(ProgressBar b) ExperienceBar
}

class ComponentBar {
  <<Default>>
  #ComponentBar(ProgressBar b) ComponentBar
}

ProgressBar <|-- ProgressBarDecorator
ProgressBar <|-- ProgressBarImpl
ProgressBarDecorator <|--	HealthBar
ProgressBarDecorator <|--	ExperienceBar
ProgressBarDecorator <|--	ComponentBar

