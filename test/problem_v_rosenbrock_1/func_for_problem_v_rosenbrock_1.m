%problem_v_rosenbrock_1
%(Rosenbrock-Funktion)
%\min_{x\in\R^2}\ 100 (x_2-x_1^2)^2+(1-x_1)^2 
%\nb -10 \leq x_1 & \leq 10 \\
%1.5 \leq x_2 & \leq 10 \\
%
function y = func_for_problem_v_rosenbrock_1(x)
	y = 100*(x(2)-x(1)^2)^2+(1-x(1))^2;
end