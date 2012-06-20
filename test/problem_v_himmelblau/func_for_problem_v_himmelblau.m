%problem_v_himmelblau
%(Himmelblau-Funktion, vgl. Beispiel 1.4.2 in \cite[S.~14~f.]{alt})
%\min_{x\in\R^2}\ (x_1^2+x_2-11)^2 + (x_1+x_2^2-7)^2 
%\nb -10 \leq x_i & \leq 10,\ i = 1,2 \\
%
function y = func_for_problem_v_himmelblau(x)
	y = (x(1)^2+x(2)-11)^2 + (x(1)+x(2)^2-7)^2;
end