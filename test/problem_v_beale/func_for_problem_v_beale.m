%problem_v_beale
%(Vgl. Beispiel 1.4.4 in \cite[S.~16]{alt})
%\min_{x\in\R^2}\ (1.5-x_1 (1-x_2))^2+(2.25-x_1 (1-x_2^2))^2+(2.625-x_1 (1-x_2^3))^2 
%\nb -10 \leq x_i & \leq 10,\ i = 1,2 \\
%
function y = func_for_problem_v_beale(x)
	y = (1.5-x(1)*(1-x(2)))^2+(2.25-x(1)*(1-x(2)^2))^2+(2.625-x(1)*(1-x(2)^3))^2;
end