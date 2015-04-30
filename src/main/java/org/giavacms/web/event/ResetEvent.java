package org.giavacms.web.event;

@SuppressWarnings("rawtypes")
public class ResetEvent
{

   private Class observedClass;

   public ResetEvent(Class Class)
   {
      super();
      this.observedClass = Class;
   }

   public ResetEvent()
   {
   }

   public Class getObservedClass()
   {
      return observedClass;
   }

   public void setObservedClass(Class observedClass)
   {
      this.observedClass = observedClass;
   }

}
