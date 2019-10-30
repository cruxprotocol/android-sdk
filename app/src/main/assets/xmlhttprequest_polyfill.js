// modules are defined as an array
// [ module function, map of requires ]
//
// map of requires is short require name -> numeric require
//
// anything defined in a previous bundle is accessed via the
// orig method which is the require for previous bundles
parcelRequire = (function (modules, cache, entry, globalName) {
  // Save the require from previous bundle to this closure if any
  var previousRequire = typeof parcelRequire === 'function' && parcelRequire;
  var nodeRequire = typeof require === 'function' && require;

  function newRequire(name, jumped) {
    if (!cache[name]) {
      if (!modules[name]) {
        // if we cannot find the module within our internal map or
        // cache jump to the current global require ie. the last bundle
        // that was added to the page.
        var currentRequire = typeof parcelRequire === 'function' && parcelRequire;
        if (!jumped && currentRequire) {
          return currentRequire(name, true);
        }

        // If there are other bundles on this page the require from the
        // previous one is saved to 'previousRequire'. Repeat this as
        // many times as there are bundles until the module is found or
        // we exhaust the require chain.
        if (previousRequire) {
          return previousRequire(name, true);
        }

        // Try the node require function if it exists.
        if (nodeRequire && typeof name === 'string') {
          return nodeRequire(name);
        }

        var err = new Error('Cannot find module \'' + name + '\'');
        err.code = 'MODULE_NOT_FOUND';
        throw err;
      }

      localRequire.resolve = resolve;
      localRequire.cache = {};

      var module = cache[name] = new newRequire.Module(name);

      modules[name][0].call(module.exports, localRequire, module, module.exports, this);
    }

    return cache[name].exports;

    function localRequire(x){
      return newRequire(localRequire.resolve(x));
    }

    function resolve(x){
      return modules[name][1][x] || x;
    }
  }

  function Module(moduleName) {
    this.id = moduleName;
    this.bundle = newRequire;
    this.exports = {};
  }

  newRequire.isParcelRequire = true;
  newRequire.Module = Module;
  newRequire.modules = modules;
  newRequire.cache = cache;
  newRequire.parent = previousRequire;
  newRequire.register = function (id, exports) {
    modules[id] = [function (require, module) {
      module.exports = exports;
    }, {}];
  };

  var error;
  for (var i = 0; i < entry.length; i++) {
    try {
      newRequire(entry[i]);
    } catch (e) {
      // Save first error but execute all entries
      if (!error) {
        error = e;
      }
    }
  }

  if (entry.length) {
    // Expose entry point to Node, AMD or browser globals
    // Based on https://github.com/ForbesLindesay/umd/blob/master/template.js
    var mainExports = newRequire(entry[entry.length - 1]);

    // CommonJS
    if (typeof exports === "object" && typeof module !== "undefined") {
      module.exports = mainExports;

    // RequireJS
    } else if (typeof define === "function" && define.amd) {
     define(function () {
       return mainExports;
     });

    // <script>
    } else if (globalName) {
      this[globalName] = mainExports;
    }
  }

  // Override the current require with this new one
  parcelRequire = newRequire;

  if (error) {
    // throw error from earlier, _after updating parcelRequire_
    throw error;
  }

  return newRequire;
})({"lZ0X":[function(require,module,exports) {
var global = arguments[3];
var win;

if (typeof window !== "undefined") {
    win = window;
} else if (typeof global !== "undefined") {
    win = global;
} else if (typeof self !== "undefined"){
    win = self;
} else {
    win = {};
}

module.exports = win;

},{}],"xIVz":[function(require,module,exports) {
module.exports = isFunction

var toString = Object.prototype.toString

function isFunction (fn) {
  var string = toString.call(fn)
  return string === '[object Function]' ||
    (typeof fn === 'function' && string !== '[object RegExp]') ||
    (typeof window !== 'undefined' &&
     // IE8 and below
     (fn === window.setTimeout ||
      fn === window.alert ||
      fn === window.confirm ||
      fn === window.prompt))
};

},{}],"B6OE":[function(require,module,exports) {
'use strict';

/* eslint no-invalid-this: 1 */

var ERROR_MESSAGE = 'Function.prototype.bind called on incompatible ';
var slice = Array.prototype.slice;
var toStr = Object.prototype.toString;
var funcType = '[object Function]';

module.exports = function bind(that) {
    var target = this;
    if (typeof target !== 'function' || toStr.call(target) !== funcType) {
        throw new TypeError(ERROR_MESSAGE + target);
    }
    var args = slice.call(arguments, 1);

    var bound;
    var binder = function () {
        if (this instanceof bound) {
            var result = target.apply(
                this,
                args.concat(slice.call(arguments))
            );
            if (Object(result) === result) {
                return result;
            }
            return this;
        } else {
            return target.apply(
                that,
                args.concat(slice.call(arguments))
            );
        }
    };

    var boundLength = Math.max(0, target.length - args.length);
    var boundArgs = [];
    for (var i = 0; i < boundLength; i++) {
        boundArgs.push('$' + i);
    }

    bound = Function('binder', 'return function (' + boundArgs.join(',') + '){ return binder.apply(this,arguments); }')(binder);

    if (target.prototype) {
        var Empty = function Empty() {};
        Empty.prototype = target.prototype;
        bound.prototype = new Empty();
        Empty.prototype = null;
    }

    return bound;
};

},{}],"TiwC":[function(require,module,exports) {
'use strict';

var implementation = require('./implementation');

module.exports = Function.prototype.bind || implementation;

},{"./implementation":"B6OE"}],"uTT0":[function(require,module,exports) {
'use strict';

var toStr = Object.prototype.toString;

module.exports = function isArguments(value) {
  var str = toStr.call(value);
  var isArgs = str === '[object Arguments]';

  if (!isArgs) {
    isArgs = str !== '[object Array]' && value !== null && typeof value === 'object' && typeof value.length === 'number' && value.length >= 0 && toStr.call(value.callee) === '[object Function]';
  }

  return isArgs;
};
},{}],"orz8":[function(require,module,exports) {
'use strict';

var keysShim;

if (!Object.keys) {
  // modified from https://github.com/es-shims/es5-shim
  var has = Object.prototype.hasOwnProperty;
  var toStr = Object.prototype.toString;

  var isArgs = require('./isArguments'); // eslint-disable-line global-require


  var isEnumerable = Object.prototype.propertyIsEnumerable;
  var hasDontEnumBug = !isEnumerable.call({
    toString: null
  }, 'toString');
  var hasProtoEnumBug = isEnumerable.call(function () {}, 'prototype');
  var dontEnums = ['toString', 'toLocaleString', 'valueOf', 'hasOwnProperty', 'isPrototypeOf', 'propertyIsEnumerable', 'constructor'];

  var equalsConstructorPrototype = function (o) {
    var ctor = o.constructor;
    return ctor && ctor.prototype === o;
  };

  var excludedKeys = {
    $applicationCache: true,
    $console: true,
    $external: true,
    $frame: true,
    $frameElement: true,
    $frames: true,
    $innerHeight: true,
    $innerWidth: true,
    $onmozfullscreenchange: true,
    $onmozfullscreenerror: true,
    $outerHeight: true,
    $outerWidth: true,
    $pageXOffset: true,
    $pageYOffset: true,
    $parent: true,
    $scrollLeft: true,
    $scrollTop: true,
    $scrollX: true,
    $scrollY: true,
    $self: true,
    $webkitIndexedDB: true,
    $webkitStorageInfo: true,
    $window: true
  };

  var hasAutomationEqualityBug = function () {
    /* global window */
    if (typeof window === 'undefined') {
      return false;
    }

    for (var k in window) {
      try {
        if (!excludedKeys['$' + k] && has.call(window, k) && window[k] !== null && typeof window[k] === 'object') {
          try {
            equalsConstructorPrototype(window[k]);
          } catch (e) {
            return true;
          }
        }
      } catch (e) {
        return true;
      }
    }

    return false;
  }();

  var equalsConstructorPrototypeIfNotBuggy = function (o) {
    /* global window */
    if (typeof window === 'undefined' || !hasAutomationEqualityBug) {
      return equalsConstructorPrototype(o);
    }

    try {
      return equalsConstructorPrototype(o);
    } catch (e) {
      return false;
    }
  };

  keysShim = function keys(object) {
    var isObject = object !== null && typeof object === 'object';
    var isFunction = toStr.call(object) === '[object Function]';
    var isArguments = isArgs(object);
    var isString = isObject && toStr.call(object) === '[object String]';
    var theKeys = [];

    if (!isObject && !isFunction && !isArguments) {
      throw new TypeError('Object.keys called on a non-object');
    }

    var skipProto = hasProtoEnumBug && isFunction;

    if (isString && object.length > 0 && !has.call(object, 0)) {
      for (var i = 0; i < object.length; ++i) {
        theKeys.push(String(i));
      }
    }

    if (isArguments && object.length > 0) {
      for (var j = 0; j < object.length; ++j) {
        theKeys.push(String(j));
      }
    } else {
      for (var name in object) {
        if (!(skipProto && name === 'prototype') && has.call(object, name)) {
          theKeys.push(String(name));
        }
      }
    }

    if (hasDontEnumBug) {
      var skipConstructor = equalsConstructorPrototypeIfNotBuggy(object);

      for (var k = 0; k < dontEnums.length; ++k) {
        if (!(skipConstructor && dontEnums[k] === 'constructor') && has.call(object, dontEnums[k])) {
          theKeys.push(dontEnums[k]);
        }
      }
    }

    return theKeys;
  };
}

module.exports = keysShim;
},{"./isArguments":"uTT0"}],"ywQn":[function(require,module,exports) {
'use strict';

var slice = Array.prototype.slice;

var isArgs = require('./isArguments');

var origKeys = Object.keys;
var keysShim = origKeys ? function keys(o) {
  return origKeys(o);
} : require('./implementation');
var originalKeys = Object.keys;

keysShim.shim = function shimObjectKeys() {
  if (Object.keys) {
    var keysWorksWithArguments = function () {
      // Safari 5.0 bug
      var args = Object.keys(arguments);
      return args && args.length === arguments.length;
    }(1, 2);

    if (!keysWorksWithArguments) {
      Object.keys = function keys(object) {
        // eslint-disable-line func-name-matching
        if (isArgs(object)) {
          return originalKeys(slice.call(object));
        }

        return originalKeys(object);
      };
    }
  } else {
    Object.keys = keysShim;
  }

  return Object.keys || keysShim;
};

module.exports = keysShim;
},{"./isArguments":"uTT0","./implementation":"orz8"}],"VxKF":[function(require,module,exports) {
'use strict';

var keys = require('object-keys');

var hasSymbols = typeof Symbol === 'function' && typeof Symbol('foo') === 'symbol';
var toStr = Object.prototype.toString;
var concat = Array.prototype.concat;
var origDefineProperty = Object.defineProperty;

var isFunction = function (fn) {
  return typeof fn === 'function' && toStr.call(fn) === '[object Function]';
};

var arePropertyDescriptorsSupported = function () {
  var obj = {};

  try {
    origDefineProperty(obj, 'x', {
      enumerable: false,
      value: obj
    }); // eslint-disable-next-line no-unused-vars, no-restricted-syntax

    for (var _ in obj) {
      // jscs:ignore disallowUnusedVariables
      return false;
    }

    return obj.x === obj;
  } catch (e) {
    /* this is IE 8. */
    return false;
  }
};

var supportsDescriptors = origDefineProperty && arePropertyDescriptorsSupported();

var defineProperty = function (object, name, value, predicate) {
  if (name in object && (!isFunction(predicate) || !predicate())) {
    return;
  }

  if (supportsDescriptors) {
    origDefineProperty(object, name, {
      configurable: true,
      enumerable: false,
      value: value,
      writable: true
    });
  } else {
    object[name] = value;
  }
};

var defineProperties = function (object, map) {
  var predicates = arguments.length > 2 ? arguments[2] : {};
  var props = keys(map);

  if (hasSymbols) {
    props = concat.call(props, Object.getOwnPropertySymbols(map));
  }

  for (var i = 0; i < props.length; i += 1) {
    defineProperty(object, props[i], map[props[i]], predicates[props[i]]);
  }
};

defineProperties.supportsDescriptors = !!supportsDescriptors;
module.exports = defineProperties;
},{"object-keys":"ywQn"}],"LENn":[function(require,module,exports) {
'use strict';
/* globals
	Atomics,
	SharedArrayBuffer,
*/

var undefined; // eslint-disable-line no-shadow-restricted-names

var ThrowTypeError = Object.getOwnPropertyDescriptor ? function () {
  return Object.getOwnPropertyDescriptor(arguments, 'callee').get;
}() : function () {
  throw new TypeError();
};
var hasSymbols = typeof Symbol === 'function' && typeof Symbol.iterator === 'symbol';

var getProto = Object.getPrototypeOf || function (x) {
  return x.__proto__;
}; // eslint-disable-line no-proto


var generator; // = function * () {};

var generatorFunction = generator ? getProto(generator) : undefined;
var asyncFn; // async function() {};

var asyncFunction = asyncFn ? asyncFn.constructor : undefined;
var asyncGen; // async function * () {};

var asyncGenFunction = asyncGen ? getProto(asyncGen) : undefined;
var asyncGenIterator = asyncGen ? asyncGen() : undefined;
var TypedArray = typeof Uint8Array === 'undefined' ? undefined : getProto(Uint8Array);
var INTRINSICS = {
  '$ %Array%': Array,
  '$ %ArrayBuffer%': typeof ArrayBuffer === 'undefined' ? undefined : ArrayBuffer,
  '$ %ArrayBufferPrototype%': typeof ArrayBuffer === 'undefined' ? undefined : ArrayBuffer.prototype,
  '$ %ArrayIteratorPrototype%': hasSymbols ? getProto([][Symbol.iterator]()) : undefined,
  '$ %ArrayPrototype%': Array.prototype,
  '$ %ArrayProto_entries%': Array.prototype.entries,
  '$ %ArrayProto_forEach%': Array.prototype.forEach,
  '$ %ArrayProto_keys%': Array.prototype.keys,
  '$ %ArrayProto_values%': Array.prototype.values,
  '$ %AsyncFromSyncIteratorPrototype%': undefined,
  '$ %AsyncFunction%': asyncFunction,
  '$ %AsyncFunctionPrototype%': asyncFunction ? asyncFunction.prototype : undefined,
  '$ %AsyncGenerator%': asyncGen ? getProto(asyncGenIterator) : undefined,
  '$ %AsyncGeneratorFunction%': asyncGenFunction,
  '$ %AsyncGeneratorPrototype%': asyncGenFunction ? asyncGenFunction.prototype : undefined,
  '$ %AsyncIteratorPrototype%': asyncGenIterator && hasSymbols && Symbol.asyncIterator ? asyncGenIterator[Symbol.asyncIterator]() : undefined,
  '$ %Atomics%': typeof Atomics === 'undefined' ? undefined : Atomics,
  '$ %Boolean%': Boolean,
  '$ %BooleanPrototype%': Boolean.prototype,
  '$ %DataView%': typeof DataView === 'undefined' ? undefined : DataView,
  '$ %DataViewPrototype%': typeof DataView === 'undefined' ? undefined : DataView.prototype,
  '$ %Date%': Date,
  '$ %DatePrototype%': Date.prototype,
  '$ %decodeURI%': decodeURI,
  '$ %decodeURIComponent%': decodeURIComponent,
  '$ %encodeURI%': encodeURI,
  '$ %encodeURIComponent%': encodeURIComponent,
  '$ %Error%': Error,
  '$ %ErrorPrototype%': Error.prototype,
  '$ %eval%': eval,
  // eslint-disable-line no-eval
  '$ %EvalError%': EvalError,
  '$ %EvalErrorPrototype%': EvalError.prototype,
  '$ %Float32Array%': typeof Float32Array === 'undefined' ? undefined : Float32Array,
  '$ %Float32ArrayPrototype%': typeof Float32Array === 'undefined' ? undefined : Float32Array.prototype,
  '$ %Float64Array%': typeof Float64Array === 'undefined' ? undefined : Float64Array,
  '$ %Float64ArrayPrototype%': typeof Float64Array === 'undefined' ? undefined : Float64Array.prototype,
  '$ %Function%': Function,
  '$ %FunctionPrototype%': Function.prototype,
  '$ %Generator%': generator ? getProto(generator()) : undefined,
  '$ %GeneratorFunction%': generatorFunction,
  '$ %GeneratorPrototype%': generatorFunction ? generatorFunction.prototype : undefined,
  '$ %Int8Array%': typeof Int8Array === 'undefined' ? undefined : Int8Array,
  '$ %Int8ArrayPrototype%': typeof Int8Array === 'undefined' ? undefined : Int8Array.prototype,
  '$ %Int16Array%': typeof Int16Array === 'undefined' ? undefined : Int16Array,
  '$ %Int16ArrayPrototype%': typeof Int16Array === 'undefined' ? undefined : Int8Array.prototype,
  '$ %Int32Array%': typeof Int32Array === 'undefined' ? undefined : Int32Array,
  '$ %Int32ArrayPrototype%': typeof Int32Array === 'undefined' ? undefined : Int32Array.prototype,
  '$ %isFinite%': isFinite,
  '$ %isNaN%': isNaN,
  '$ %IteratorPrototype%': hasSymbols ? getProto(getProto([][Symbol.iterator]())) : undefined,
  '$ %JSON%': JSON,
  '$ %JSONParse%': JSON.parse,
  '$ %Map%': typeof Map === 'undefined' ? undefined : Map,
  '$ %MapIteratorPrototype%': typeof Map === 'undefined' || !hasSymbols ? undefined : getProto(new Map()[Symbol.iterator]()),
  '$ %MapPrototype%': typeof Map === 'undefined' ? undefined : Map.prototype,
  '$ %Math%': Math,
  '$ %Number%': Number,
  '$ %NumberPrototype%': Number.prototype,
  '$ %Object%': Object,
  '$ %ObjectPrototype%': Object.prototype,
  '$ %ObjProto_toString%': Object.prototype.toString,
  '$ %ObjProto_valueOf%': Object.prototype.valueOf,
  '$ %parseFloat%': parseFloat,
  '$ %parseInt%': parseInt,
  '$ %Promise%': typeof Promise === 'undefined' ? undefined : Promise,
  '$ %PromisePrototype%': typeof Promise === 'undefined' ? undefined : Promise.prototype,
  '$ %PromiseProto_then%': typeof Promise === 'undefined' ? undefined : Promise.prototype.then,
  '$ %Promise_all%': typeof Promise === 'undefined' ? undefined : Promise.all,
  '$ %Promise_reject%': typeof Promise === 'undefined' ? undefined : Promise.reject,
  '$ %Promise_resolve%': typeof Promise === 'undefined' ? undefined : Promise.resolve,
  '$ %Proxy%': typeof Proxy === 'undefined' ? undefined : Proxy,
  '$ %RangeError%': RangeError,
  '$ %RangeErrorPrototype%': RangeError.prototype,
  '$ %ReferenceError%': ReferenceError,
  '$ %ReferenceErrorPrototype%': ReferenceError.prototype,
  '$ %Reflect%': typeof Reflect === 'undefined' ? undefined : Reflect,
  '$ %RegExp%': RegExp,
  '$ %RegExpPrototype%': RegExp.prototype,
  '$ %Set%': typeof Set === 'undefined' ? undefined : Set,
  '$ %SetIteratorPrototype%': typeof Set === 'undefined' || !hasSymbols ? undefined : getProto(new Set()[Symbol.iterator]()),
  '$ %SetPrototype%': typeof Set === 'undefined' ? undefined : Set.prototype,
  '$ %SharedArrayBuffer%': typeof SharedArrayBuffer === 'undefined' ? undefined : SharedArrayBuffer,
  '$ %SharedArrayBufferPrototype%': typeof SharedArrayBuffer === 'undefined' ? undefined : SharedArrayBuffer.prototype,
  '$ %String%': String,
  '$ %StringIteratorPrototype%': hasSymbols ? getProto(''[Symbol.iterator]()) : undefined,
  '$ %StringPrototype%': String.prototype,
  '$ %Symbol%': hasSymbols ? Symbol : undefined,
  '$ %SymbolPrototype%': hasSymbols ? Symbol.prototype : undefined,
  '$ %SyntaxError%': SyntaxError,
  '$ %SyntaxErrorPrototype%': SyntaxError.prototype,
  '$ %ThrowTypeError%': ThrowTypeError,
  '$ %TypedArray%': TypedArray,
  '$ %TypedArrayPrototype%': TypedArray ? TypedArray.prototype : undefined,
  '$ %TypeError%': TypeError,
  '$ %TypeErrorPrototype%': TypeError.prototype,
  '$ %Uint8Array%': typeof Uint8Array === 'undefined' ? undefined : Uint8Array,
  '$ %Uint8ArrayPrototype%': typeof Uint8Array === 'undefined' ? undefined : Uint8Array.prototype,
  '$ %Uint8ClampedArray%': typeof Uint8ClampedArray === 'undefined' ? undefined : Uint8ClampedArray,
  '$ %Uint8ClampedArrayPrototype%': typeof Uint8ClampedArray === 'undefined' ? undefined : Uint8ClampedArray.prototype,
  '$ %Uint16Array%': typeof Uint16Array === 'undefined' ? undefined : Uint16Array,
  '$ %Uint16ArrayPrototype%': typeof Uint16Array === 'undefined' ? undefined : Uint16Array.prototype,
  '$ %Uint32Array%': typeof Uint32Array === 'undefined' ? undefined : Uint32Array,
  '$ %Uint32ArrayPrototype%': typeof Uint32Array === 'undefined' ? undefined : Uint32Array.prototype,
  '$ %URIError%': URIError,
  '$ %URIErrorPrototype%': URIError.prototype,
  '$ %WeakMap%': typeof WeakMap === 'undefined' ? undefined : WeakMap,
  '$ %WeakMapPrototype%': typeof WeakMap === 'undefined' ? undefined : WeakMap.prototype,
  '$ %WeakSet%': typeof WeakSet === 'undefined' ? undefined : WeakSet,
  '$ %WeakSetPrototype%': typeof WeakSet === 'undefined' ? undefined : WeakSet.prototype
};

module.exports = function GetIntrinsic(name, allowMissing) {
  if (arguments.length > 1 && typeof allowMissing !== 'boolean') {
    throw new TypeError('"allowMissing" argument must be a boolean');
  }

  var key = '$ ' + name;

  if (!(key in INTRINSICS)) {
    throw new SyntaxError('intrinsic ' + name + ' does not exist!');
  } // istanbul ignore if // hopefully this is impossible to test :-)


  if (typeof INTRINSICS[key] === 'undefined' && !allowMissing) {
    throw new TypeError('intrinsic ' + name + ' exists, but is not available. Please file an issue!');
  }

  return INTRINSICS[key];
};
},{}],"ar57":[function(require,module,exports) {
'use strict';

var bind = require('function-bind');

module.exports = bind.call(Function.call, Object.prototype.hasOwnProperty);
},{"function-bind":"TiwC"}],"mBar":[function(require,module,exports) {
'use strict';

var GetIntrinsic = require('../GetIntrinsic');

var $TypeError = GetIntrinsic('%TypeError%');
var $SyntaxError = GetIntrinsic('%SyntaxError%');

var has = require('has');

var predicates = {
  // https://ecma-international.org/ecma-262/6.0/#sec-property-descriptor-specification-type
  'Property Descriptor': function isPropertyDescriptor(ES, Desc) {
    if (ES.Type(Desc) !== 'Object') {
      return false;
    }

    var allowed = {
      '[[Configurable]]': true,
      '[[Enumerable]]': true,
      '[[Get]]': true,
      '[[Set]]': true,
      '[[Value]]': true,
      '[[Writable]]': true
    };

    for (var key in Desc) {
      // eslint-disable-line
      if (has(Desc, key) && !allowed[key]) {
        return false;
      }
    }

    var isData = has(Desc, '[[Value]]');
    var IsAccessor = has(Desc, '[[Get]]') || has(Desc, '[[Set]]');

    if (isData && IsAccessor) {
      throw new $TypeError('Property Descriptors may not be both accessor and data descriptors');
    }

    return true;
  }
};

module.exports = function assertRecord(ES, recordType, argumentName, value) {
  var predicate = predicates[recordType];

  if (typeof predicate !== 'function') {
    throw new $SyntaxError('unknown record type: ' + recordType);
  }

  if (!predicate(ES, value)) {
    throw new $TypeError(argumentName + ' must be a ' + recordType);
  }
};
},{"../GetIntrinsic":"LENn","has":"ar57"}],"bplQ":[function(require,module,exports) {
'use strict';

var GetIntrinsic = require('../GetIntrinsic');

var has = require('has');

var $TypeError = GetIntrinsic('%TypeError%');

module.exports = function IsPropertyDescriptor(ES, Desc) {
  if (ES.Type(Desc) !== 'Object') {
    return false;
  }

  var allowed = {
    '[[Configurable]]': true,
    '[[Enumerable]]': true,
    '[[Get]]': true,
    '[[Set]]': true,
    '[[Value]]': true,
    '[[Writable]]': true
  };

  for (var key in Desc) {
    // eslint-disable-line
    if (has(Desc, key) && !allowed[key]) {
      return false;
    }
  }

  if (ES.IsDataDescriptor(Desc) && ES.IsAccessorDescriptor(Desc)) {
    throw new $TypeError('Property Descriptors may not be both accessor and data descriptors');
  }

  return true;
};
},{"../GetIntrinsic":"LENn","has":"ar57"}],"TD7O":[function(require,module,exports) {
'use strict';

module.exports = Number.isNaN || function isNaN(a) {
  return a !== a;
};
},{}],"lJl4":[function(require,module,exports) {
'use strict';

var $isNaN = Number.isNaN || function (a) {
  return a !== a;
};

module.exports = Number.isFinite || function (x) {
  return typeof x === 'number' && !$isNaN(x) && x !== Infinity && x !== -Infinity;
};
},{}],"mhfJ":[function(require,module,exports) {
'use strict';

module.exports = function sign(number) {
  return number >= 0 ? 1 : -1;
};
},{}],"eYhx":[function(require,module,exports) {
'use strict';

module.exports = function mod(number, modulo) {
  var remain = number % modulo;
  return Math.floor(remain >= 0 ? remain : remain + modulo);
};
},{}],"b4C7":[function(require,module,exports) {
'use strict';

var fnToStr = Function.prototype.toString;
var constructorRegex = /^\s*class\b/;

var isES6ClassFn = function isES6ClassFunction(value) {
  try {
    var fnStr = fnToStr.call(value);
    return constructorRegex.test(fnStr);
  } catch (e) {
    return false; // not a function
  }
};

var tryFunctionObject = function tryFunctionToStr(value) {
  try {
    if (isES6ClassFn(value)) {
      return false;
    }

    fnToStr.call(value);
    return true;
  } catch (e) {
    return false;
  }
};

var toStr = Object.prototype.toString;
var fnClass = '[object Function]';
var genClass = '[object GeneratorFunction]';
var hasToStringTag = typeof Symbol === 'function' && typeof Symbol.toStringTag === 'symbol';

module.exports = function isCallable(value) {
  if (!value) {
    return false;
  }

  if (typeof value !== 'function' && typeof value !== 'object') {
    return false;
  }

  if (typeof value === 'function' && !value.prototype) {
    return true;
  }

  if (hasToStringTag) {
    return tryFunctionObject(value);
  }

  if (isES6ClassFn(value)) {
    return false;
  }

  var strClass = toStr.call(value);
  return strClass === fnClass || strClass === genClass;
};
},{}],"mnG2":[function(require,module,exports) {
module.exports = function isPrimitive(value) {
  return value === null || typeof value !== 'function' && typeof value !== 'object';
};
},{}],"v4E7":[function(require,module,exports) {
'use strict';

var toStr = Object.prototype.toString;

var isPrimitive = require('./helpers/isPrimitive');

var isCallable = require('is-callable'); // http://ecma-international.org/ecma-262/5.1/#sec-8.12.8


var ES5internalSlots = {
  '[[DefaultValue]]': function (O) {
    var actualHint;

    if (arguments.length > 1) {
      actualHint = arguments[1];
    } else {
      actualHint = toStr.call(O) === '[object Date]' ? String : Number;
    }

    if (actualHint === String || actualHint === Number) {
      var methods = actualHint === String ? ['toString', 'valueOf'] : ['valueOf', 'toString'];
      var value, i;

      for (i = 0; i < methods.length; ++i) {
        if (isCallable(O[methods[i]])) {
          value = O[methods[i]]();

          if (isPrimitive(value)) {
            return value;
          }
        }
      }

      throw new TypeError('No default value');
    }

    throw new TypeError('invalid [[DefaultValue]] hint supplied');
  }
}; // http://ecma-international.org/ecma-262/5.1/#sec-9.1

module.exports = function ToPrimitive(input) {
  if (isPrimitive(input)) {
    return input;
  }

  if (arguments.length > 1) {
    return ES5internalSlots['[[DefaultValue]]'](input, arguments[1]);
  }

  return ES5internalSlots['[[DefaultValue]]'](input);
};
},{"./helpers/isPrimitive":"mnG2","is-callable":"b4C7"}],"ITpX":[function(require,module,exports) {
'use strict';

var bind = require('function-bind');

var GetIntrinsic = require('../GetIntrinsic');

var $Function = GetIntrinsic('%Function%');
var $apply = $Function.apply;
var $call = $Function.call;

module.exports = function callBind() {
  return bind.apply($call, arguments);
};

module.exports.apply = function applyBind() {
  return bind.apply($apply, arguments);
};
},{"function-bind":"TiwC","../GetIntrinsic":"LENn"}],"SIdQ":[function(require,module,exports) {
'use strict';

var GetIntrinsic = require('./GetIntrinsic');

var $Object = GetIntrinsic('%Object%');
var $TypeError = GetIntrinsic('%TypeError%');
var $String = GetIntrinsic('%String%');
var $Number = GetIntrinsic('%Number%');

var assertRecord = require('./helpers/assertRecord');

var isPropertyDescriptor = require('./helpers/isPropertyDescriptor');

var $isNaN = require('./helpers/isNaN');

var $isFinite = require('./helpers/isFinite');

var sign = require('./helpers/sign');

var mod = require('./helpers/mod');

var IsCallable = require('is-callable');

var toPrimitive = require('es-to-primitive/es5');

var has = require('has');

var callBind = require('./helpers/callBind');

var strSlice = callBind($String.prototype.slice);

var isPrefixOf = function isPrefixOf(prefix, string) {
  if (prefix === string) {
    return true;
  }

  if (prefix.length > string.length) {
    return false;
  }

  return strSlice(string, 0, prefix.length) === prefix;
}; // https://es5.github.io/#x9


var ES5 = {
  ToPrimitive: toPrimitive,
  ToBoolean: function ToBoolean(value) {
    return !!value;
  },
  ToNumber: function ToNumber(value) {
    return +value; // eslint-disable-line no-implicit-coercion
  },
  ToInteger: function ToInteger(value) {
    var number = this.ToNumber(value);

    if ($isNaN(number)) {
      return 0;
    }

    if (number === 0 || !$isFinite(number)) {
      return number;
    }

    return sign(number) * Math.floor(Math.abs(number));
  },
  ToInt32: function ToInt32(x) {
    return this.ToNumber(x) >> 0;
  },
  ToUint32: function ToUint32(x) {
    return this.ToNumber(x) >>> 0;
  },
  ToUint16: function ToUint16(value) {
    var number = this.ToNumber(value);

    if ($isNaN(number) || number === 0 || !$isFinite(number)) {
      return 0;
    }

    var posInt = sign(number) * Math.floor(Math.abs(number));
    return mod(posInt, 0x10000);
  },
  ToString: function ToString(value) {
    return $String(value);
  },
  ToObject: function ToObject(value) {
    this.CheckObjectCoercible(value);
    return $Object(value);
  },
  CheckObjectCoercible: function CheckObjectCoercible(value, optMessage) {
    /* jshint eqnull:true */
    if (value == null) {
      throw new $TypeError(optMessage || 'Cannot call method on ' + value);
    }

    return value;
  },
  IsCallable: IsCallable,
  SameValue: function SameValue(x, y) {
    if (x === y) {
      // 0 === -0, but they are not identical.
      if (x === 0) {
        return 1 / x === 1 / y;
      }

      return true;
    }

    return $isNaN(x) && $isNaN(y);
  },
  // https://www.ecma-international.org/ecma-262/5.1/#sec-8
  Type: function Type(x) {
    if (x === null) {
      return 'Null';
    }

    if (typeof x === 'undefined') {
      return 'Undefined';
    }

    if (typeof x === 'function' || typeof x === 'object') {
      return 'Object';
    }

    if (typeof x === 'number') {
      return 'Number';
    }

    if (typeof x === 'boolean') {
      return 'Boolean';
    }

    if (typeof x === 'string') {
      return 'String';
    }
  },
  // https://ecma-international.org/ecma-262/6.0/#sec-property-descriptor-specification-type
  IsPropertyDescriptor: function IsPropertyDescriptor(Desc) {
    return isPropertyDescriptor(this, Desc);
  },
  // https://ecma-international.org/ecma-262/5.1/#sec-8.10.1
  IsAccessorDescriptor: function IsAccessorDescriptor(Desc) {
    if (typeof Desc === 'undefined') {
      return false;
    }

    assertRecord(this, 'Property Descriptor', 'Desc', Desc);

    if (!has(Desc, '[[Get]]') && !has(Desc, '[[Set]]')) {
      return false;
    }

    return true;
  },
  // https://ecma-international.org/ecma-262/5.1/#sec-8.10.2
  IsDataDescriptor: function IsDataDescriptor(Desc) {
    if (typeof Desc === 'undefined') {
      return false;
    }

    assertRecord(this, 'Property Descriptor', 'Desc', Desc);

    if (!has(Desc, '[[Value]]') && !has(Desc, '[[Writable]]')) {
      return false;
    }

    return true;
  },
  // https://ecma-international.org/ecma-262/5.1/#sec-8.10.3
  IsGenericDescriptor: function IsGenericDescriptor(Desc) {
    if (typeof Desc === 'undefined') {
      return false;
    }

    assertRecord(this, 'Property Descriptor', 'Desc', Desc);

    if (!this.IsAccessorDescriptor(Desc) && !this.IsDataDescriptor(Desc)) {
      return true;
    }

    return false;
  },
  // https://ecma-international.org/ecma-262/5.1/#sec-8.10.4
  FromPropertyDescriptor: function FromPropertyDescriptor(Desc) {
    if (typeof Desc === 'undefined') {
      return Desc;
    }

    assertRecord(this, 'Property Descriptor', 'Desc', Desc);

    if (this.IsDataDescriptor(Desc)) {
      return {
        value: Desc['[[Value]]'],
        writable: !!Desc['[[Writable]]'],
        enumerable: !!Desc['[[Enumerable]]'],
        configurable: !!Desc['[[Configurable]]']
      };
    } else if (this.IsAccessorDescriptor(Desc)) {
      return {
        get: Desc['[[Get]]'],
        set: Desc['[[Set]]'],
        enumerable: !!Desc['[[Enumerable]]'],
        configurable: !!Desc['[[Configurable]]']
      };
    } else {
      throw new $TypeError('FromPropertyDescriptor must be called with a fully populated Property Descriptor');
    }
  },
  // https://ecma-international.org/ecma-262/5.1/#sec-8.10.5
  ToPropertyDescriptor: function ToPropertyDescriptor(Obj) {
    if (this.Type(Obj) !== 'Object') {
      throw new $TypeError('ToPropertyDescriptor requires an object');
    }

    var desc = {};

    if (has(Obj, 'enumerable')) {
      desc['[[Enumerable]]'] = this.ToBoolean(Obj.enumerable);
    }

    if (has(Obj, 'configurable')) {
      desc['[[Configurable]]'] = this.ToBoolean(Obj.configurable);
    }

    if (has(Obj, 'value')) {
      desc['[[Value]]'] = Obj.value;
    }

    if (has(Obj, 'writable')) {
      desc['[[Writable]]'] = this.ToBoolean(Obj.writable);
    }

    if (has(Obj, 'get')) {
      var getter = Obj.get;

      if (typeof getter !== 'undefined' && !this.IsCallable(getter)) {
        throw new TypeError('getter must be a function');
      }

      desc['[[Get]]'] = getter;
    }

    if (has(Obj, 'set')) {
      var setter = Obj.set;

      if (typeof setter !== 'undefined' && !this.IsCallable(setter)) {
        throw new $TypeError('setter must be a function');
      }

      desc['[[Set]]'] = setter;
    }

    if ((has(desc, '[[Get]]') || has(desc, '[[Set]]')) && (has(desc, '[[Value]]') || has(desc, '[[Writable]]'))) {
      throw new $TypeError('Invalid property descriptor. Cannot both specify accessors and a value or writable attribute');
    }

    return desc;
  },
  // https://www.ecma-international.org/ecma-262/5.1/#sec-11.9.3
  'Abstract Equality Comparison': function AbstractEqualityComparison(x, y) {
    var xType = this.Type(x);
    var yType = this.Type(y);

    if (xType === yType) {
      return x === y; // ES6+ specified this shortcut anyways.
    }

    if (x == null && y == null) {
      return true;
    }

    if (xType === 'Number' && yType === 'String') {
      return this['Abstract Equality Comparison'](x, this.ToNumber(y));
    }

    if (xType === 'String' && yType === 'Number') {
      return this['Abstract Equality Comparison'](this.ToNumber(x), y);
    }

    if (xType === 'Boolean') {
      return this['Abstract Equality Comparison'](this.ToNumber(x), y);
    }

    if (yType === 'Boolean') {
      return this['Abstract Equality Comparison'](x, this.ToNumber(y));
    }

    if ((xType === 'String' || xType === 'Number') && yType === 'Object') {
      return this['Abstract Equality Comparison'](x, this.ToPrimitive(y));
    }

    if (xType === 'Object' && (yType === 'String' || yType === 'Number')) {
      return this['Abstract Equality Comparison'](this.ToPrimitive(x), y);
    }

    return false;
  },
  // https://www.ecma-international.org/ecma-262/5.1/#sec-11.9.6
  'Strict Equality Comparison': function StrictEqualityComparison(x, y) {
    var xType = this.Type(x);
    var yType = this.Type(y);

    if (xType !== yType) {
      return false;
    }

    if (xType === 'Undefined' || xType === 'Null') {
      return true;
    }

    return x === y; // shortcut for steps 4-7
  },
  // https://www.ecma-international.org/ecma-262/5.1/#sec-11.8.5
  // eslint-disable-next-line max-statements
  'Abstract Relational Comparison': function AbstractRelationalComparison(x, y, LeftFirst) {
    if (this.Type(LeftFirst) !== 'Boolean') {
      throw new $TypeError('Assertion failed: LeftFirst argument must be a Boolean');
    }

    var px;
    var py;

    if (LeftFirst) {
      px = this.ToPrimitive(x, $Number);
      py = this.ToPrimitive(y, $Number);
    } else {
      py = this.ToPrimitive(y, $Number);
      px = this.ToPrimitive(x, $Number);
    }

    var bothStrings = this.Type(px) === 'String' && this.Type(py) === 'String';

    if (!bothStrings) {
      var nx = this.ToNumber(px);
      var ny = this.ToNumber(py);

      if ($isNaN(nx) || $isNaN(ny)) {
        return undefined;
      }

      if ($isFinite(nx) && $isFinite(ny) && nx === ny) {
        return false;
      }

      if (nx === 0 && ny === 0) {
        return false;
      }

      if (nx === Infinity) {
        return false;
      }

      if (ny === Infinity) {
        return true;
      }

      if (ny === -Infinity) {
        return false;
      }

      if (nx === -Infinity) {
        return true;
      }

      return nx < ny; // by now, these are both nonzero, finite, and not equal
    }

    if (isPrefixOf(py, px)) {
      return false;
    }

    if (isPrefixOf(px, py)) {
      return true;
    }

    return px < py; // both strings, neither a prefix of the other. shortcut for steps c-f
  }
};
module.exports = ES5;
},{"./GetIntrinsic":"LENn","./helpers/assertRecord":"mBar","./helpers/isPropertyDescriptor":"bplQ","./helpers/isNaN":"TD7O","./helpers/isFinite":"lJl4","./helpers/sign":"mhfJ","./helpers/mod":"eYhx","is-callable":"b4C7","es-to-primitive/es5":"v4E7","has":"ar57","./helpers/callBind":"ITpX"}],"ZtmO":[function(require,module,exports) {
'use strict';

var bind = require('function-bind');

var ES = require('es-abstract/es5');

var replace = bind.call(Function.call, String.prototype.replace);
/* eslint-disable no-control-regex */

var leftWhitespace = /^[\x09\x0A\x0B\x0C\x0D\x20\xA0\u1680\u180E\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200A\u202F\u205F\u3000\u2028\u2029\uFEFF]+/;
var rightWhitespace = /[\x09\x0A\x0B\x0C\x0D\x20\xA0\u1680\u180E\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200A\u202F\u205F\u3000\u2028\u2029\uFEFF]+$/;
/* eslint-enable no-control-regex */

module.exports = function trim() {
  var S = ES.ToString(ES.CheckObjectCoercible(this));
  return replace(replace(S, leftWhitespace, ''), rightWhitespace, '');
};
},{"function-bind":"TiwC","es-abstract/es5":"SIdQ"}],"NEt2":[function(require,module,exports) {
'use strict';

var implementation = require('./implementation');

var zeroWidthSpace = '\u200b';

module.exports = function getPolyfill() {
  if (String.prototype.trim && zeroWidthSpace.trim() === zeroWidthSpace) {
    return String.prototype.trim;
  }

  return implementation;
};
},{"./implementation":"ZtmO"}],"VBgO":[function(require,module,exports) {

'use strict';

var define = require('define-properties');

var getPolyfill = require('./polyfill');

module.exports = function shimStringTrim() {
  var polyfill = getPolyfill();
  define(String.prototype, {
    trim: polyfill
  }, {
    trim: function testTrim() {
      return String.prototype.trim !== polyfill;
    }
  });
  return polyfill;
};
},{"define-properties":"VxKF","./polyfill":"NEt2"}],"NwWY":[function(require,module,exports) {

'use strict';

var bind = require('function-bind');

var define = require('define-properties');

var implementation = require('./implementation');

var getPolyfill = require('./polyfill');

var shim = require('./shim');

var boundTrim = bind.call(Function.call, getPolyfill());
define(boundTrim, {
  getPolyfill: getPolyfill,
  implementation: implementation,
  shim: shim
});
module.exports = boundTrim;
},{"function-bind":"TiwC","define-properties":"VxKF","./implementation":"ZtmO","./polyfill":"NEt2","./shim":"VBgO"}],"FXvN":[function(require,module,exports) {
'use strict';

var isCallable = require('is-callable');

var toStr = Object.prototype.toString;
var hasOwnProperty = Object.prototype.hasOwnProperty;

var forEachArray = function forEachArray(array, iterator, receiver) {
    for (var i = 0, len = array.length; i < len; i++) {
        if (hasOwnProperty.call(array, i)) {
            if (receiver == null) {
                iterator(array[i], i, array);
            } else {
                iterator.call(receiver, array[i], i, array);
            }
        }
    }
};

var forEachString = function forEachString(string, iterator, receiver) {
    for (var i = 0, len = string.length; i < len; i++) {
        // no such thing as a sparse string.
        if (receiver == null) {
            iterator(string.charAt(i), i, string);
        } else {
            iterator.call(receiver, string.charAt(i), i, string);
        }
    }
};

var forEachObject = function forEachObject(object, iterator, receiver) {
    for (var k in object) {
        if (hasOwnProperty.call(object, k)) {
            if (receiver == null) {
                iterator(object[k], k, object);
            } else {
                iterator.call(receiver, object[k], k, object);
            }
        }
    }
};

var forEach = function forEach(list, iterator, thisArg) {
    if (!isCallable(iterator)) {
        throw new TypeError('iterator must be a function');
    }

    var receiver;
    if (arguments.length >= 3) {
        receiver = thisArg;
    }

    if (toStr.call(list) === '[object Array]') {
        forEachArray(list, iterator, receiver);
    } else if (typeof list === 'string') {
        forEachString(list, iterator, receiver);
    } else {
        forEachObject(list, iterator, receiver);
    }
};

module.exports = forEach;

},{"is-callable":"b4C7"}],"q9uR":[function(require,module,exports) {
var trim = require('string.prototype.trim')
  , forEach = require('for-each')
  , isArray = function(arg) {
      return Object.prototype.toString.call(arg) === '[object Array]';
    }

module.exports = function (headers) {
  if (!headers)
    return {}

  var result = {}

  forEach(
      trim(headers).split('\n')
    , function (row) {
        var index = row.indexOf(':')
          , key = trim(row.slice(0, index)).toLowerCase()
          , value = trim(row.slice(index + 1))

        if (typeof(result[key]) === 'undefined') {
          result[key] = value
        } else if (isArray(result[key])) {
          result[key].push(value)
        } else {
          result[key] = [ result[key], value ]
        }
      }
  )

  return result
}

},{"string.prototype.trim":"NwWY","for-each":"FXvN"}],"K5Tb":[function(require,module,exports) {
module.exports = extend;
var hasOwnProperty = Object.prototype.hasOwnProperty;

function extend() {
  var target = {};

  for (var i = 0; i < arguments.length; i++) {
    var source = arguments[i];

    for (var key in source) {
      if (hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }

  return target;
}
},{}],"iMim":[function(require,module,exports) {
"use strict";
var window = require("global/window")
var isFunction = require("is-function")
var parseHeaders = require("parse-headers")
var xtend = require("xtend")

module.exports = createXHR
// Allow use of default import syntax in TypeScript
module.exports.default = createXHR;
createXHR.XMLHttpRequest = window.XMLHttpRequest || noop
createXHR.XDomainRequest = "withCredentials" in (new createXHR.XMLHttpRequest()) ? createXHR.XMLHttpRequest : window.XDomainRequest

forEachArray(["get", "put", "post", "patch", "head", "delete"], function(method) {
    createXHR[method === "delete" ? "del" : method] = function(uri, options, callback) {
        options = initParams(uri, options, callback)
        options.method = method.toUpperCase()
        return _createXHR(options)
    }
})

function forEachArray(array, iterator) {
    for (var i = 0; i < array.length; i++) {
        iterator(array[i])
    }
}

function isEmpty(obj){
    for(var i in obj){
        if(obj.hasOwnProperty(i)) return false
    }
    return true
}

function initParams(uri, options, callback) {
    var params = uri

    if (isFunction(options)) {
        callback = options
        if (typeof uri === "string") {
            params = {uri:uri}
        }
    } else {
        params = xtend(options, {uri: uri})
    }

    params.callback = callback
    return params
}

function createXHR(uri, options, callback) {
    options = initParams(uri, options, callback)
    return _createXHR(options)
}

function _createXHR(options) {
    if(typeof options.callback === "undefined"){
        throw new Error("callback argument missing")
    }

    var called = false
    var callback = function cbOnce(err, response, body){
        if(!called){
            called = true
            options.callback(err, response, body)
        }
    }

    function readystatechange() {
        if (xhr.readyState === 4) {
            setTimeout(loadFunc, 0)
        }
    }

    function getBody() {
        // Chrome with requestType=blob throws errors arround when even testing access to responseText
        var body = undefined

        if (xhr.response) {
            body = xhr.response
        } else {
            body = xhr.responseText || getXml(xhr)
        }

        if (isJson) {
            try {
                body = JSON.parse(body)
            } catch (e) {}
        }

        return body
    }

    function errorFunc(evt) {
        clearTimeout(timeoutTimer)
        if(!(evt instanceof Error)){
            evt = new Error("" + (evt || "Unknown XMLHttpRequest Error") )
        }
        evt.statusCode = 0
        return callback(evt, failureResponse)
    }

    // will load the data & process the response in a special response object
    function loadFunc() {
        if (aborted) return
        var status
        clearTimeout(timeoutTimer)
        if(options.useXDR && xhr.status===undefined) {
            //IE8 CORS GET successful response doesn't have a status field, but body is fine
            status = 200
        } else {
            status = (xhr.status === 1223 ? 204 : xhr.status)
        }
        var response = failureResponse
        var err = null

        if (status !== 0){
            response = {
                body: getBody(),
                statusCode: status,
                method: method,
                headers: {},
                url: uri,
                rawRequest: xhr
            }
            if(xhr.getAllResponseHeaders){ //remember xhr can in fact be XDR for CORS in IE
                response.headers = parseHeaders(xhr.getAllResponseHeaders())
            }
        } else {
            err = new Error("Internal XMLHttpRequest Error")
        }
        return callback(err, response, response.body)
    }

    var xhr = options.xhr || null

    if (!xhr) {
        if (options.cors || options.useXDR) {
            xhr = new createXHR.XDomainRequest()
        }else{
            xhr = new createXHR.XMLHttpRequest()
        }
    }

    var key
    var aborted
    var uri = xhr.url = options.uri || options.url
    var method = xhr.method = options.method || "GET"
    var body = options.body || options.data
    var headers = xhr.headers = options.headers || {}
    var sync = !!options.sync
    var isJson = false
    var timeoutTimer
    var failureResponse = {
        body: undefined,
        headers: {},
        statusCode: 0,
        method: method,
        url: uri,
        rawRequest: xhr
    }

    if ("json" in options && options.json !== false) {
        isJson = true
        headers["accept"] || headers["Accept"] || (headers["Accept"] = "application/json") //Don't override existing accept header declared by user
        if (method !== "GET" && method !== "HEAD") {
            headers["content-type"] || headers["Content-Type"] || (headers["Content-Type"] = "application/json") //Don't override existing accept header declared by user
            body = JSON.stringify(options.json === true ? body : options.json)
        }
    }

    xhr.onreadystatechange = readystatechange
    xhr.onload = loadFunc
    xhr.onerror = errorFunc
    // IE9 must have onprogress be set to a unique function.
    xhr.onprogress = function () {
        // IE must die
    }
    xhr.onabort = function(){
        aborted = true;
    }
    xhr.ontimeout = errorFunc
    xhr.open(method, uri, !sync, options.username, options.password)
    //has to be after open
    if(!sync) {
        xhr.withCredentials = !!options.withCredentials
    }
    // Cannot set timeout with sync request
    // not setting timeout on the xhr object, because of old webkits etc. not handling that correctly
    // both npm's request and jquery 1.x use this kind of timeout, so this is being consistent
    if (!sync && options.timeout > 0 ) {
        timeoutTimer = setTimeout(function(){
            if (aborted) return
            aborted = true//IE9 may still call readystatechange
            xhr.abort("timeout")
            var e = new Error("XMLHttpRequest timeout")
            e.code = "ETIMEDOUT"
            errorFunc(e)
        }, options.timeout )
    }

    if (xhr.setRequestHeader) {
        for(key in headers){
            if(headers.hasOwnProperty(key)){
                xhr.setRequestHeader(key, headers[key])
            }
        }
    } else if (options.headers && !isEmpty(options.headers)) {
        throw new Error("Headers cannot be set on an XDomainRequest object")
    }

    if ("responseType" in options) {
        xhr.responseType = options.responseType
    }

    if ("beforeSend" in options &&
        typeof options.beforeSend === "function"
    ) {
        options.beforeSend(xhr)
    }

    // Microsoft Edge browser sends "undefined" when send is called with undefined value.
    // XMLHttpRequest spec says to pass null as body to indicate no body
    // See https://github.com/naugtur/xhr/issues/100.
    xhr.send(body || null)

    return xhr


}

function getXml(xhr) {
    // xhr.responseXML will throw Exception "InvalidStateError" or "DOMException"
    // See https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/responseXML.
    try {
        if (xhr.responseType === "document") {
            return xhr.responseXML
        }
        var firefoxBugTakenEffect = xhr.responseXML && xhr.responseXML.documentElement.nodeName === "parsererror"
        if (xhr.responseType === "" && !firefoxBugTakenEffect) {
            return xhr.responseXML
        }
    } catch (e) {}

    return null
}

function noop() {}

},{"global/window":"lZ0X","is-function":"xIVz","parse-headers":"q9uR","xtend":"K5Tb"}],"C2Oq":[function(require,module,exports) {
"use strict";

var foo2 = _interopRequireWildcard(require("xhr"));

function _getRequireWildcardCache() { if (typeof WeakMap !== "function") return null; var cache = new WeakMap(); _getRequireWildcardCache = function () { return cache; }; return cache; }

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } var cache = _getRequireWildcardCache(); if (cache && cache.has(obj)) { return cache.get(obj); } var newObj = {}; if (obj != null) { var hasPropertyDescriptor = Object.defineProperty && Object.getOwnPropertyDescriptor; for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) { var desc = hasPropertyDescriptor ? Object.getOwnPropertyDescriptor(obj, key) : null; if (desc && (desc.get || desc.set)) { Object.defineProperty(newObj, key, desc); } else { newObj[key] = obj[key]; } } } } newObj.default = obj; if (cache) { cache.set(obj, newObj); } return newObj; }

// import * as CruxPay from "./index";
// declare global {
//     interface Window {
//         CruxPay: object;
//     }
// }
// window.CruxPay = CruxPay;
// export let foo = () => {
//     return "abc";
// };
// @ts-ignore
window.XMLHttpRequest = foo2.XMLHttpRequest;
},{"xhr":"iMim"}]},{},["C2Oq"], null)
//# sourceMappingURL=/xmlhttprequest_polyfill.js.map