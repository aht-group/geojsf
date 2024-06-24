import {GeoJSF} from './geo';
import * as ol from 'ol';
import { GeoJsfViewport } from './viewport';
import { GeoJsfUtil } from './geo-util';

console.log("This ist GeoJSF 3 ... using OpenLayers version " +ol.VERSION);

(window as any).GeoJSF = GeoJSF;
(window as any).GeoJsfViewport = GeoJsfViewport;
(window as any).GeoJsfUtil = GeoJsfUtil;