function H = hess_rosenbrock_func_1(x)
	H = [ 1200*x(1)^2-400*x(2)+2  -400*x(1);
              -400*x(1)              200  ];
end