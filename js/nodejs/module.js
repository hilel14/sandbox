var ModuleOne = (function() {
	var x = 1;
	var y = 2;
	var f = function() {console.log("x + y = " + (x + y))};
	return {f:f};
})();

ModuleOne.f();

var ModuleTwo = function() {
	var x = 10;
	var y = 20;
	var config={a:1,b:2};
	return {cfg:config};
};

var moduleTwo = ModuleTwo();
console.log("module 2 config:");
console.log(" a = " + moduleTwo.cfg.a);
console.log(" b = " + moduleTwo.cfg.b);
console.log(" x = " + moduleTwo.x);
