function g = grad_func_for_problem_v_rosenbrock(x)
	g = [-400*x(1)*(x(2)-x(1)^2)-2*(1-x(1)); 200*(x(2)-x(1)^2)];
end