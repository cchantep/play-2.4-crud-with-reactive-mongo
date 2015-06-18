import org.openqa.selenium.WebDriver
import play.api.test._
import play.api.{ Application, ApplicationLoader, Environment, Mode, BuiltInComponents, BuiltInComponentsFromContext, Logger }
import play.api.ApplicationLoader.Context
import play.api.test.Helpers

class WithRealApplication extends WithApplicationLoader(new MacwiredApplicationLoader)
class WithTestApplication extends WithApplicationLoader(new TestApplicationLoader)
class WithTestApplicationInBrowser[W <: WebDriver]
  extends WithBrowser[W](webDriver = WebDriverFactory(Helpers.FIREFOX), app = new TestApplicationLoader().load(ApplicationLoader.createContext(new Environment(new java.io.File("."), ApplicationLoader.getClass.getClassLoader, Mode.Test))))

class TestApplicationLoader extends ApplicationLoader {
  def load(context: Context): Application = {
    Logger.configure(context.environment)
    (new BuiltInComponentsFromContext(context) with Components with TestAppComponents).application
  }
}
