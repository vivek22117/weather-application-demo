profile        = "admin"
owner_team     = "DD-Team"
default_region = "us-east-1"

component_name = "weather-api"
ecs_task_mode  = "bridge"

service_desired_count = 2
service_launch_type   = "EC2"

default_target_group_port = 8001