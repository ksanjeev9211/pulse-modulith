package com.sanjeev.pulse.modulith;

import com.sanjeev.pulse.PulseModulithApplication;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModulithArchitectureTest {

  @Test
  void verifiesModuleStructure() {

    ApplicationModules.of(PulseModulithApplication.class).forEach(System.out::println);
    ApplicationModules.of("com.sanjeev.pulse").verify();
  }
}
