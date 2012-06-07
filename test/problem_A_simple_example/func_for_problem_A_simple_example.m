%problem_A_simple_example
%\min_{x\in\R^5}\ \| x \|^2
%\nb x_1 + x_2 + x_3 + x_4 + x_5 & = 1 \\
%
function y = func_for_problem_A_simple_example(x)
	xd = [0; 0; 0; 0; 0];
	y = norm(x-xd)^2;
end