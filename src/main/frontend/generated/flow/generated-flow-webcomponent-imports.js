import { injectGlobalWebcomponentCss } from 'Frontend/generated/jar-resources/theme-util.js';

import '@vaadin/vertical-layout/src/vaadin-vertical-layout.js';
import '@vaadin/text-field/src/vaadin-text-field.js';
import '@vaadin/tooltip/src/vaadin-tooltip.js';
import '@vaadin/email-field/src/vaadin-email-field.js';
import '@vaadin/horizontal-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/button/src/vaadin-button.js';
import 'Frontend/generated/jar-resources/disableOnClickFunctions.js';
import '@vaadin/notification/src/vaadin-notification.js';
import 'Frontend/generated/jar-resources/flow-component-renderer.js';
import 'Frontend/generated/jar-resources/flow-component-directive.js';
import '@vaadin/app-layout/src/vaadin-app-layout.js';
import '@vaadin/integer-field/src/vaadin-integer-field.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import 'Frontend/generated/jar-resources/ReactRouterOutletElement.tsx';

const loadOnDemand = (key) => {
  const pending = [];
  if (key === '0cf8d77a12786f99a98c38e6e0222fce71ed36f88e42beef6538b78267d05f04') {
    pending.push(import('./chunks/chunk-cf79a3df4b603f3ed19b2f775884e0ddef317f206ff2c56331559d06265ef4c1.js'));
  }
  if (key === 'f7d69b7bb19a9ddbbc669297e97d3f5b5919087cf3e2854cae6573ad57f1f04b') {
    pending.push(import('./chunks/chunk-d84738c46c57c24b876372bd8682eb760566e2707f50673ccc7a25b0bff0443e.js'));
  }
  if (key === '0f0c75be6205121c8f8eefe672502c7b37e35df3f2f05f08b2177803da249d33') {
    pending.push(import('./chunks/chunk-d3c723a67d25800bbafff45f540ad6722053abdec72430749d2d4eaa51707e59.js'));
  }
  if (key === 'e7da681cefbfd838f6c3e6d1310df4d9468179fdc3e982dc07b361e984021a42') {
    pending.push(import('./chunks/chunk-cf79a3df4b603f3ed19b2f775884e0ddef317f206ff2c56331559d06265ef4c1.js'));
  }
  return Promise.all(pending);
}
window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}