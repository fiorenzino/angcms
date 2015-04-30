package org.giavacms.web.event;

import org.giavacms.web.model.BaseRole;

public class RoleEvent
{

   private BaseRole role;

   public RoleEvent(BaseRole role)
   {
      super();
      this.role = role;
   }

   public BaseRole getRole()
   {
      return role;
   }

   public void setRole(BaseRole role)
   {
      this.role = role;
   }

}
