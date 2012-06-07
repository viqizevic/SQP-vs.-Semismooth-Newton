%problem_v_bazaraa_shetty_1
%\min_{x\in\R^2}\ (x_1-2)^4+(x_1-2 x_2)^2 
%\nb 4 \leq x_1 & \leq 10 \\
%-10 \leq x_2 & \leq 10 \\
%
function y = func_for_problem_v_bazaraa_shetty_1(x)
	y = (x(1)-2)^4+(x(1)-2*x(2))^2;
end