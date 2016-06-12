package projara.test;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import jess.Rete;

@Singleton
@Remote(TestJessRemote.class)
@Startup
public class TestJessBean implements TestJessRemote {

	@Override
	@PostConstruct
	public void testJess() throws Exception{
		/*Rete engine = new Rete();
		try {
			engine.eval("(printout t \"Hello Projara!!!!\" crlf)");
		} catch (JessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		Rete engine = new Rete();
		//TestData td = new TestData();

		engine.reset();

		engine.eval("(watch all)");

		engine.batch("projara/resources/jess/model_templates.clp");
		
		engine.eval("(printout t \"Hello Projara!!!!\" crlf)");
		
		engine.batch("projara/test/test.clp");
		
		engine.eval("(printout t \"Hello Projara!!!!\" crlf)");
		

	}

}
