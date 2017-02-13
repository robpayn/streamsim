package ktrans;

import org.payn.chsm.Resource;
import org.payn.chsm.io.OutputHandlerFactoryXML;
import org.payn.chsm.io.file.OutputHandlerBehaviorFactoryXML;
import org.payn.chsm.io.logger.LoggerSystemOut;
import org.payn.chsm.io.xml.ElementOutput;
import org.payn.chsm.io.xml.ElementResource;
import org.payn.chsm.processors.ControllerHolon;
import org.payn.neoch.MatrixBuilder;
import org.payn.neoch.io.MatrixBuilderXML;
import org.payn.neoch.io.MatrixLoaderXML;
import org.payn.neoch.io.OutputHandlerXMLSerialFactoryXML;
import org.payn.neoch.processors.ControllerNEORKTwo;
import org.payn.resources.particle.ResourceParticle;
import org.payn.resources.solute.ResourceSolute;

/**
 * Matrix loader implementing some default configuration
 * 
 * @author robpayn
 *
 */
public class StreamSimulatorMatrixLoader extends MatrixLoaderXML {
   
   /**
    * Serial output handler name
    */
   private static final String OUTPUT_HANDLER_SERIAL = "serial";
   
   /**
    * Behavior output handler name
    */
   private static final String OUTPUT_HANDLER_BEHAVIOR = "behavior";

   /**
    * Behavior output handler name
    */
   private static final String OUTPUT_HANDLER_TASCC = "tascc";

   @Override
   protected void initializeLoggers() throws Exception {
      loggerList.add(LoggerSystemOut.class);
      super.initializeLoggers();
   }   

   @Override
   protected MatrixBuilder createBuilder() throws Exception 
   {
      MatrixBuilder builder = super.createBuilder();
      if (builder == null)
      {
         builder = new MatrixBuilderXML();
      }
      return builder;
   }
   
   @Override
   protected ControllerHolon getController() throws Exception 
   {
      ControllerHolon controller = super.getController();
      if (controller == null)
      {
         controller = new ControllerNEORKTwo();
      }
      return controller;
   }
   
   @Override
   protected Resource getResource(ElementResource resourceElem) throws Exception 
   {
      Resource resource = super.getResource(resourceElem);
      if (resource == null)
      {
         if (resourceElem.getName().equals("particle"))
         {
            resource = new ResourceParticle();
         }
         else
         {
            resource = new ResourceSolute();
         }
      }
      return resource;
   }
   
   @Override
   protected OutputHandlerFactoryXML<?> getOutputHandlerFactory(ElementOutput outputElem) throws Exception 
   {
      OutputHandlerFactoryXML<?> factory = super.getOutputHandlerFactory(outputElem);
      if (factory == null)
      {
         switch(outputElem.getName())
         {
            case OUTPUT_HANDLER_SERIAL:
               factory = new OutputHandlerXMLSerialFactoryXML();
               break;
            case OUTPUT_HANDLER_BEHAVIOR:
               factory = new OutputHandlerBehaviorFactoryXML();
               break;
            case OUTPUT_HANDLER_TASCC:
               factory = new OutputHandlerTASCCFactoryXML();
               break;
         }
      }
      return factory;
   }

}
