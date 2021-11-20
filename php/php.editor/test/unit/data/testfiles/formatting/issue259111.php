<?php
$func1 = function(){echo "anonymous function1" . PHP_EOL;};
($func1)();

$func2 = function() {
    return function() {echo "anonymous function2" . PHP_EOL;};
};
(($func2)())();

(function(){echo "anonymous function3" . PHP_EOL;})();

(function($param1, $param2) {echo "anonymous function4" . PHP_EOL;})("param1", "param2");

class Bar { public function __invoke() {echo "class __invoke" . PHP_EOL;} }
(new Bar())();
(new Bar)();

(new class { public function __invoke() {echo "anonymous class __invoke" . PHP_EOL;} })();

echo 'strlen'('scalar call') . PHP_EOL;
$something = 'something';
echo 'strlen'($something) . PHP_EOL;
echo ("strlen")($something) . PHP_EOL;

$a = "strlen";
$b = "a";
echo ($$b)("foo") . PHP_EOL;

class ArrayCall { public static function test() { echo "array call" . PHP_EOL;} }
$array = new ArrayCall();
['ArrayCall', 'test']();
(['ArrayCall', 'test'])();
[new ArrayCall, 'test']();
[new ArrayCall(), 'test']();
array('ArrayCall', 'test')();
array($array, 'test')();
$test = "test";
//[new class {public static function test($param) {echo "anonymous class array call" . PHP_EOL;}}, $test]("test");

class Foo
{
    private $callable;

    public function __construct(callable $callable)
    {
        $this->callable = $callable;
    }

    public function doSomething()
    {
        ($this->callable)();
    }
}

