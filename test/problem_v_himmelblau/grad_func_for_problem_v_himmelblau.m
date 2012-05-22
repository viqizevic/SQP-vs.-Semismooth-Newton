function g = grad_func_for_problem_v_himmelblau(x)
	g = [ 4*x(1)*(x(1)^2+x(2)-11) + 2*(x(1)+x(2)^2-7);
		  2*(x(1)^2+x(2)-11) + 4*x(2)*(x(1)+x(2)^2-7) ];
end