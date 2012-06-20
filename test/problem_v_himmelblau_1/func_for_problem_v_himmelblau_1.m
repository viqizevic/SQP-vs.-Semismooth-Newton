%problem_v_himmelblau_1
%(Himmelblau-Funktion)
%\min_{x\in\R^2}\ (x_1^2+x_2-11)^2 + (x_1+x_2^2-7)^2 
%\nb -10 \leq x_1 & \leq 10 \\
%5 \leq x_2 & \leq 10 \\
%
function y = func_for_problem_v_himmelblau_1(x)
	y = (x(1)^2+x(2)-11)^2 + (x(1)+x(2)^2-7)^2;
end